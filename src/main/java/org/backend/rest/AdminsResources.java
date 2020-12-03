package org.backend.rest;

import org.backend.modals.Admin;
import org.backend.modals.Personne;
import org.backend.repository.AdminRepo;
import org.backend.repository.AdminRepoInt;
import org.backend.repository.UrgencierRepo;
import org.backend.repository.qualifiers.AdminQualifier;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminsResources {
    @Inject @AdminQualifier
    AdminRepo adminRepo;

    @DELETE
    @Path("/admin:{login}")
    public Response delete(@PathParam("login")String login) {
        Long id = adminRepo.getUserId(login);
        adminRepo.delete(id);
        return Response.noContent().build();
    }
    @GET
    @Path("/admin:{login}")
    public Response find(@PathParam("login")String login) {
        Long id = adminRepo.getUserId(login);
        Personne user = adminRepo.find(id);
        return Response.ok(user)
                .build();   
    }

    @PUT
    @Path("/admin:{login}")
    public Response update(@PathParam("login")String login, Admin user){
        Long id = adminRepo.getUserId(login);
        adminRepo.update(id, user);
        return Response.ok(user).build();
    }

    @GET
    public Response findAll(@BeanParam FilterBean filterBean) {
        List<Admin> users = adminRepo.findAll();
        if(filterBean.getUserId() > 0)
            users = users.subList(filterBean.getUserId(), users.size());
        return ((users == null)?
                Response.noContent().build():
                Response.ok(users).build());
    }
}
