package org.backend.rest;

import org.backend.modals.Personne;
import org.backend.modals.Urgencier;
import org.backend.repository.UrgencierRepo;
import org.backend.repository.UrgencierRepoInt;
import org.backend.repository.UserRepo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UrgenciersResources {
    @Inject
    UrgencierRepo urgencierRepo;

    @DELETE
    @Path("/urgencier:{login}")
    public Response delete(@PathParam("login")String login) {
        Long id = urgencierRepo.getUserId(login);
        urgencierRepo.delete(id);
        return Response.noContent().build();
    }
    @GET
    @Path("/urgencier:{login}")
    public Response find(@PathParam("login")String login) {
        Long id = urgencierRepo.getUserId(login);
        Personne user = urgencierRepo.find(id);
        return Response.ok(user).build();
    }

    @PUT
    @Path("/urgencier:{login}")
    public Response update(@PathParam("login")String login, Urgencier user){
        Long id = urgencierRepo.getUserId(login);
        urgencierRepo.update(id, user);
        return Response.ok(user).build();
    }

    @GET
    @Path("/count")
    public Response countAll() {
        Long count = urgencierRepo.countAll();
        return Response.ok(count).build();
    }

    @GET
    public Response findAll(@BeanParam FilterBean filterBean) {
        List<Urgencier> users = urgencierRepo.findAll();
        if(filterBean.getUserId() > 0)
            users = users.subList(filterBean.getUserId(), users.size());
        return ((users == null)?
                Response.noContent().build():
                Response.ok(users).build());
    }
}
