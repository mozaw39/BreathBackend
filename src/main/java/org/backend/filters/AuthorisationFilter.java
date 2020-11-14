package org.backend.filters;

import org.backend.modals.*;
import org.backend.repository.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@Provider
@Named
public class AuthorisationFilter implements ContainerRequestFilter {
    private static final String AUTHORISATION_HEADER_KEY = "Authorization";
    private static final CharSequence SECURED_URL = "authenticated";
    private static final String AUTHORISATION_HEADER_PREFIX = "Basic ";
    private static final String USER_OR_PASSWORD_IS_INCORRECT = "username or password is incorrect";
    private static final String AUTHENTICATED = "authenticated";
    private static final String PASSWORD_IS_INCORRECT = "password is incorrect";
    private static final String YOU_ARE_NOT_AUTHENTICATED = "you are not authenticated";
    private static final String UNAUTHORIZED = "unauthorized";
    private static final String USERS_PATH = "users";
    private static final String URGENCIERS_PATH = "urgenciers";
    private static final String USER_PATH = "user";
    private static final String URGENCIER_PATH = "urgencier";
    private static final String ADMINS_PATH = "admins";
    private static final String CANDIDATS_PATH = "candidats";
    private static final String ALL_USERS = "allusers";
    String[] tokens;
    @Inject
    UserRepoInt userRepo;
    @Inject
    UrgencierRepoInt urgencierRepo;
    @Inject
    AdminRepoInt adminRepo;
    @Inject
    Repository repository;
    @Inject
    CandidatRepoInt candidatRepo;

    private String userFromPath;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        Personne whoIsThis = null;
        UriInfo uriInfo = containerRequestContext.getUriInfo();
        List<String> authHeader = containerRequestContext.getHeaders()
                .get(AUTHORISATION_HEADER_KEY);
        if(isSecuredPath(uriInfo)) { // verify if the user has sent any credentials
                if(authHeader != null && authHeader.size() > 0) {
                String authToken = authHeader.get(0);
                tokens = getStrings(authToken);
                whoIsThis = getUserType(tokens);
            }
                // on a mis cette fonction à l'exterieur pour traiter le cas ou l'utilisateur n'envoie rien
            handleUnauthorizedRes(containerRequestContext, authorizationInfo(whoIsThis, uriInfo));
        }
    }

    private Personne getUserType(String[] tokens) {
        String username = tokens[0];
        String password = tokens[1];
        if(adminRepo.isPasswordCorrect(username, password))
            return new Admin();
        else if(urgencierRepo.isPasswordCorrect(username, password))
            return new Urgencier();
        else if(userRepo.isPasswordCorrect(username, password))
            return new SimpleUser();
        else if(candidatRepo.isPasswordCorrect(username, password))
            return new Candidat();
        return null;
    }

    private boolean isSecuredPath(UriInfo uriInfo) {
        return (uriInfo.getPath().contains(SECURED_URL) &&( uriInfo.getPath().contains(USERS_PATH)
                || uriInfo.getPath().contains(URGENCIERS_PATH) || uriInfo.getPath().contains(ADMINS_PATH)
        || uriInfo.getPath().contains(ALL_USERS))) || uriInfo.getPath().contains(SECURED_URL);
    }


    private String authorizationInfo(Personne userClass, UriInfo uriInfo) {
        if(userClass == null)
            return YOU_ARE_NOT_AUTHENTICATED;
        String username = tokens[0];
        String password = tokens[1];
        // cette condition est ajoutée pour vérifier les infos de l'utilisateur pour chaque envoi
        //
        if(!repository.isUserOrPasswordCorrect(username, password))
            return USER_OR_PASSWORD_IS_INCORRECT;
        //si il est admin, grant it all for him
        if(userClass instanceof Admin)
            return AUTHENTICATED;
        //on traite chaque role séparemment avec ses propres ressources.
       if(userClass instanceof Candidat)
           return authenticationInfo(uriInfo,username, CANDIDATS_PATH);
        return AUTHENTICATED;
    }

    private String isAccessGranted(UriInfo uriInfo,String username, String userBaseUrl) {
        String path = uriInfo.getPath();
        StringTokenizer pathTokens = new StringTokenizer(path, "/");
        if(path.contains(userBaseUrl)){
            while(pathTokens.hasMoreTokens()) { //les ressources que le "user" peut utiliser se trouvent apres "/{usertype}" (/candidats, /urgenciers)
                String usernameFromPath = pathTokens.nextToken();
                if(usernameFromPath.equals(username))
                    return AUTHENTICATED;
            }
        }
        return YOU_ARE_NOT_AUTHENTICATED;
    }

    private String authenticationInfo(UriInfo uriInfo, String username, String userBaseUrl) {
        String path = uriInfo.getPath();
        String userFromPath;
        String userlogin = tokens[0];
        StringTokenizer pathTokens = new StringTokenizer(path, "/");
        while(pathTokens.hasMoreTokens()){
            String pathToken = pathTokens.nextToken();
            if(pathToken.equals(ALL_USERS))
                // L'appel au service d'authentication se fait sur BASE_URL/allusers/{userLogin}
                if(pathTokens.hasMoreTokens()) { //cette condition assure que personne autre que l'admin n'a acces à /alluser.
                    userFromPath = pathTokens.nextToken();
                    if(userFromPath.equals(userlogin))
                        return AUTHENTICATED;
                }
        }
        return isAccessGranted(uriInfo,username, userBaseUrl);
    }

    private void handleUnauthorizedRes(ContainerRequestContext containerRequestContext, String authorizationInfo) {
        if(authorizationInfo.equals(AUTHENTICATED))
            return;
        Response unauthorizedResponse = Response.status(Response.Status.UNAUTHORIZED)
                                                .entity(authorizationInfo)
                                                .build();
        containerRequestContext.abortWith(unauthorizedResponse);
    }

    private String[] getStrings(String authToken) {
        authToken = authToken.replace(AUTHORISATION_HEADER_PREFIX, "");
        String decodedString = new String(Base64.getDecoder().decode(authToken.getBytes()));
        StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
        String[] tokens = new String[2];
        int index = 0;
        while(tokenizer.hasMoreTokens()){
            tokens[index++] = tokenizer.nextToken();
        }
        return tokens;
    }
}
