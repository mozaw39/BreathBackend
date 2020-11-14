package org.backend.rest;

import org.backend.modals.*;
import org.backend.repository.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/Inscription")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class Inscription {
    private int count;
    @Inject
    UserRepoInt userRepo;
    @Inject
    UrgencierRepoInt urgencierRepo;
    @Inject
    AdminRepoInt adminRepo;
    @Inject
    RepositoryInt repository;

    @POST
    @Path("/admins")
    public Response createAdmin(Admin inscrit, @Context UriInfo uriInfo) {
        inscrit.setUserType(UserType.ADMIN);
        Personne user = repository.create(inscrit);
        URI uriForUser = getUrlForUser(user, uriInfo);
        //ADD LINKS
        //user.addLink(uriForUser.toString(), "self");
        //user = repository.update(user.getUserId(), user);
        return Response.created(uriForUser).entity(user).build();
    }

    @POST
    @Path("/users")
    public Response create(SimpleUser inscrit, @Context UriInfo uriInfo) {
        inscrit.setUserType(UserType.USER);
        Personne user = repository.create(inscrit);
        URI uriForUser = getUrlForUser(user, uriInfo);
        //ADD LINKS
        //user.addLink(uriForUser.toString(), "self");
        //user = repository.update(user.getUserId(), user);
        return Response.created(uriForUser).entity(user).build();
    }
    @POST
    @Path("/candidats")
    public Response createCandidat(Candidat inscrit, @Context UriInfo uriInfo) {
        inscrit.setUserType(UserType.CANDIDAT);
        Personne user = repository.create(inscrit);
        URI uriForUser = getUrlForUser(user, uriInfo);
        //ADD LINKS
        //user.addLink(uriForUser.toString(), "self");
        //user = repository.update(user.getUserId(), user);
        return Response.created(uriForUser).entity(user).build();
    }
    @POST
    @Path("/urgenciers")
    public Response createUrgencier(Urgencier inscrit, @Context UriInfo uriInfo) {
        inscrit.setUserType(UserType.URGENCIER);
        Personne user = repository.create(inscrit);
        URI uriForUser = getUrlForUser(user, uriInfo);
        //ADD LINKS
        //user.addLink(uriForUser.toString(), "self");
        //user = repository.update(user.getUserId(), user);
        return Response.created(uriForUser).entity(user).build();
    }

    private URI getUrlForUser(Personne user, UriInfo uriInfo) {
        URI uri = uriInfo.getAbsolutePathBuilder().path("user:" + user.getLogin()).build();
        return uri;
    }
    private URI getUrlForUrgencier(Urgencier user, UriInfo uriInfo) {
        URI uri = uriInfo.getAbsolutePathBuilder().path("urgenciers").path("urgencier:" + user.getLogin()).build();
        return uri;
    }

}
