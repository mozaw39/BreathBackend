package org.backend.rest;

import org.backend.exception.Exceptions;
import org.backend.modals.*;
import org.backend.repository.*;
import org.backend.repository.qualifiers.RepositoryQualifier;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/Inscription")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class Inscription {
    @Inject @RepositoryQualifier
    Repository repository;

    @POST
    @Path("/admins")
    public Response createAdmin(Admin inscrit, @Context UriInfo uriInfo) {
        inscrit.setUserType(UserType.ADMIN);
        Personne user;
        try{
            user = repository.create(inscrit);
        }catch (Exception e){
            throw new Exceptions.AlreadyExistException("element already exist");
        }
        URI uriForUser = getUrl(user, uriInfo);
        //ADD LINKS
        //user.addLink(uriForUser.toString(), "self");
        //user = repository.update(user.getUserId(), user);
        return Response.created(uriForUser).entity(user).build();
    }

    @POST
    @Path("/users")
    public Response create(SimpleUser inscrit, @Context UriInfo uriInfo) {
        inscrit.setUserType(UserType.USER);
        Personne user;
        try{
            user = repository.create(inscrit);
        }catch (Exception e){
            throw new Exceptions.AlreadyExistException("element already exist");
        }
        URI uriForUser = getUrl(user, uriInfo);
        //ADD LINKS
        //user.addLink(uriForUser.toString(), "self");
        //user = repository.update(user.getUserId(), user);
        return Response.created(uriForUser).entity(user).build();
    }
    @POST
    @Path("/candidats")
    public Response createCandidat(Candidat inscrit, @Context UriInfo uriInfo) {
        inscrit.setUserType(UserType.CANDIDAT);
        Personne user;
        try{
            user = repository.create(inscrit);
        }catch (Throwable e){
            throw new Exceptions.AlreadyExistException("element already exists!");
        }
        URI uriForUser = getUrl(user, uriInfo);
        //ADD LINKS
        //user.addLink(uriForUser.toString(), "self");
        //user = repository.update(user.getUserId(), user);
        return Response.created(uriForUser).entity(user).build();
    }
    @POST
    @Path("/urgenciers")
    public Response createUrgencier(Urgencier inscrit, @Context UriInfo uriInfo) {
        inscrit.setUserType(UserType.URGENCIER);
        Personne user;
        try{
            user = repository.create(inscrit);
        }catch (Exception e){
            throw new Exceptions.AlreadyExistException("element already exist");
        }
        URI uriForUser = getUrl(user, uriInfo);
        //ADD LINKS
        //user.addLink(uriForUser.toString(), "self");
        //user = repository.update(user.getUserId(), user);
        return Response.created(uriForUser).entity(user).build();
    }


    private URI getUrl(Personne user, UriInfo uriInfo) {
        URI uri = uriInfo.getAbsolutePathBuilder().path("allusers").path(user.getLogin()).build();
        return uri;
    }

}
