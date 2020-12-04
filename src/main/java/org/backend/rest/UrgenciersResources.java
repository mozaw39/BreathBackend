package org.backend.rest;

import org.backend.modals.Personne;
import org.backend.modals.Urgencier;
import org.backend.repository.Repository;
import org.backend.repository.qualifiers.RepositoryQualifier;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UrgenciersResources {
    @Inject @RepositoryQualifier
    Repository urgencierRepo;

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
        Personne user = urgencierRepo.find(id, Urgencier.class);
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
    public Response findAll(@BeanParam FilterBean filterBean) {
        List<Urgencier> users = urgencierRepo.findAll()
                .stream()
                .map( element -> (Urgencier)element )
                .collect(Collectors.toList());
        if(filterBean.getUserId() > 0)
            users = users.subList(filterBean.getUserId(), users.size());
        return ((users == null)?
                Response.noContent().build():
                Response.ok(users).build());
    }
}
