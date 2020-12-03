package org.backend.rest;

import org.backend.modals.Admin;
import org.backend.modals.Personne;
import org.backend.repository.Repository;
import org.backend.repository.RepositoryInt;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/authenticated")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticatedResources {
    @Context
    ResourceContext context;
    @Inject
    Repository repository;

    //Cette ressource est crée pour permettre une authentification souple, on ne va pas s'amuser à tester
    // sur toutes les autres /candidads, /admins /urgenciers...
    @GET
    @Path("/allusers")
    public Response findAll(@BeanParam FilterBean filterBean) {
        List<Personne> users = repository.findAll();
        if(filterBean.getUserId() > 0)
            users = users.subList(filterBean.getUserId(), users.size());
        return ((users == null)?
                Response.noContent().build():
                Response.ok(users).build());
    }

    @GET
    @Path("/allusers/{login}")
    public Response find(@PathParam("login")String login) {
        Long id = repository.getUserId(login);
        Personne user = repository.find(id);
        return Response.ok(user)
                .build();
    }

    @PUT
    @Path("/allusers/{login}")
    public Response update(@PathParam("login")String login, Personne user){
        Long id = repository.getUserId(login);
        repository.update(id, user);
        return Response.ok(user).build();
    }

    @Path("/users")
    public SimpleUsersResources getSimpleUserRessources(){
        return this.context.getResource(SimpleUsersResources.class);
    }
    @Path("/urgenciers")
    public UrgenciersResources getUrgenciersRessources(){
        return this.context.getResource(UrgenciersResources.class);
    }
    @Path("/admins")
    public AdminsResources getAdminsRessources(){
        return this.context.getResource(AdminsResources.class);
    }
    @Path("/candidats")
    public CandidatsResources getCandidatsRessources(){
        return this.context.getResource(CandidatsResources.class);
    }


}
