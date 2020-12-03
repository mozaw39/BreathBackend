package org.backend.rest;

import org.backend.modals.Personne;
import org.backend.modals.SimpleUser;
import org.backend.repository.UserRepo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SimpleUsersResources {
    @Inject
    UserRepo userRepo;

    @DELETE
    @Path("/user:{login}")
    public Response delete(@PathParam("login")String login) {
        Long id = userRepo.getUserId(login);
        userRepo.delete(id);
        return Response.noContent().build();
    }
    @GET
    @Path("/user:{login}")
    public Response find(@PathParam("login")String login) {
        Long id = userRepo.getUserId(login);
        Personne user = userRepo.find(id);
        return Response.ok(user).build();
    }

    @PUT
    @Path("/user:{login}")
    public Response update(@PathParam("login")String login, SimpleUser user){
        Long id = userRepo.getUserId(login);
        userRepo.update(id, user);
        return Response.ok(user).build();
    }

    @GET
    @Path("/count")
    public Response countAll() {
        Long count = userRepo.countAll();
        return Response.ok(count).build();
    }

    @GET
    public Response findAll(@BeanParam FilterBean filterBean) {
        List<SimpleUser> users = userRepo.findAll();
        if(filterBean.getUserId() > 0)
            users = users.subList(filterBean.getUserId(), users.size());
        return ((users == null)?
                Response.noContent().build():
                Response.ok(users).build());
    }

}
