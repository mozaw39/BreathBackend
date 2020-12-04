package org.backend.rest;

import org.backend.modals.Personne;
import org.backend.modals.SimpleUser;
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
public class SimpleUsersResources {
    @Inject @RepositoryQualifier
    Repository userRepo;

    @DELETE
    @Path("/{login}")
    public Response delete(@PathParam("login")String login) {
        Long id = userRepo.getUserId(login);
        userRepo.delete(id);
        return Response.noContent().build();
    }
    @GET
    @Path("/{login}")
    public Response find(@PathParam("login")String login) {
        Long id = userRepo.getUserId(login);
        Personne user = userRepo.find(id, SimpleUser.class);
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
    public Response findAll(@BeanParam FilterBean filterBean) {
        List<SimpleUser> users = userRepo.findAll(SimpleUser.class).
                stream()
                .map( element -> (SimpleUser)element )
                .collect(Collectors.toList());
        if(filterBean.getUserId() > 0)
            users = users.subList(filterBean.getUserId(), users.size());
        return ((users == null)?
                Response.noContent().build():
                Response.ok(users).build());
    }

}
