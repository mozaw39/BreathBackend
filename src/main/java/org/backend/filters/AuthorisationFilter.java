package org.backend.filters;

import org.backend.modals.*;
import org.backend.repository.*;
import org.backend.repository.qualifiers.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import java.util.*;

import static org.backend.filters.Roles.getRoles;

@Provider
@Named
public class AuthorisationFilter implements ContainerRequestFilter {
    private static final String AUTHORISATION_HEADER_KEY = "Authorization";
    private static final String SECURED_URL = "authenticated";
    private static final String AUTHORISATION_HEADER_PREFIX = "Basic ";
    private static final String AUTHENTICATED = "authenticated";
    private static final String YOU_ARE_NOT_AUTHENTICATED = "you are not authenticated";
    private static final String ALL_USERS = "allusers";
    Map<String, String> tokens;
    String uriInfo;
    @Inject @RepositoryQualifier
    Repository repository;
    List<Role> roles = getRoles();

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        Personne currentUser = null;
        uriInfo = containerRequestContext.getUriInfo().getPath();
        List<String> authHeader = containerRequestContext.getHeaders()
                .get(AUTHORISATION_HEADER_KEY);
        //verifier si la ressource demandée est sécurisé.
        if(isSecuredPath(uriInfo)) {
                 uriInfo = uriInfo.replace("/" + AUTHENTICATED + "/", ""); //no need to carry on
                if(authHeader != null && authHeader.size() > 0) {// verify if the user has sent any credentials
                String authToken = authHeader.get(0);
                tokens = getTokens(authToken);
                    currentUser = getAuthenticatedUser(tokens, roles);
            }
                // on a mis cette fonction à l'exterieur pour traiter le cas ou l'utilisateur n'envoie rien
            handleUnauthorizedRes(containerRequestContext, authorizationInfo(currentUser, roles, uriInfo));
        }
    }
    //Authentication
    // auth resource: BASE_URL/authenticated/allusers/{login}
    private Personne getAuthenticatedUser(Map<String, String> tokens, List<Role> roles) {
        String username = tokens.get("username");
        String password = tokens.get("password");
        uriInfo = uriInfo.replace(ALL_USERS + "/" + username, ""); //no need to hold this info
        Personne user = repository.isUserExist(username, password);
        //if username and password are correct return the ref of the object
        if(user != null)
        for (Role role : roles) {
            if(user.getClass().isInstance(role.roleClass()))
                return role.roleClass();
        }
        return null;
    }

    private boolean isSecuredPath(String uriInfo) {
        return uriInfo.contains(SECURED_URL);
    }


    private String authorizationInfo(Personne userClass,List<Role> roles, String uriInfo) {
        if(userClass == null)
            return YOU_ARE_NOT_AUTHENTICATED;
        if(uriInfo.equals("")) // verify if the user asks only for authentication resource
            return AUTHENTICATED;
        String username = tokens.get("username");
        //si il est admin, grant it all for him
        if(userClass instanceof Admin)
            return AUTHENTICATED;
        for (Role role : roles) {
            if(userClass.getClass().isInstance(role.roleClass()))
                return isAccessGranted(uriInfo,username, role.roleBaseResource());
        }
        return AUTHENTICATED;
    }

    private String isAccessGranted(String path,String username, String userBaseUrl) {
        StringTokenizer pathTokens = new StringTokenizer(path, "/");
        if(path.contains(userBaseUrl)){
            while(pathTokens.hasMoreTokens()) { //les ressources que le "user" peut utiliser se trouvent apres "/{usertype}/{userlogin}" (/candidats, /urgenciers)
                String pathToken = pathTokens.nextToken();
                if(pathToken.equals(userBaseUrl) && pathTokens.hasMoreTokens()
                        && pathTokens.nextToken().equals(username)) //make sure to only grant access to {usertype}/{userlogin}...
                    return AUTHENTICATED;
            }
        }
        return YOU_ARE_NOT_AUTHENTICATED;
    }

    private void handleUnauthorizedRes(ContainerRequestContext containerRequestContext, String authorizationInfo) {
        if(authorizationInfo.equals(AUTHENTICATED))
            return;
        Response unauthorizedResponse = Response.status(Response.Status.UNAUTHORIZED)
                                                .entity(authorizationInfo)
                                                .build();
        containerRequestContext.abortWith(unauthorizedResponse);
    }
    //get tokens from user
    private Map<String, String> getTokens(String authToken) {
        authToken = authToken.replace(AUTHORISATION_HEADER_PREFIX, "");
        String decodedString = new String(Base64.getDecoder().decode(authToken.getBytes()));
        StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
        Map tokens = new HashMap<String,String>();
        tokens.put("username", tokenizer.nextToken());
        tokens.put("password", tokenizer.nextToken());
        return tokens;
    }
}
