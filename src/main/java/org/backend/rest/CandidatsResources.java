package org.backend.rest;

import org.backend.modals.Candidat;
import org.backend.modals.Personne;
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
public class CandidatsResources {
    @Inject @RepositoryQualifier
    Repository candidatRepo;

    @DELETE
    @Path("/{login}")
    public Response delete(@PathParam("login")String login) {
        Long id = candidatRepo.getUserId(login);
        candidatRepo.delete(id);
        return Response.noContent().build();
    }
    @GET
    @Path("/{login}")
    public Response find(@PathParam("login")String login) {
        Long id = candidatRepo.getUserId(login);
        Personne user = candidatRepo.find(id, Candidat.class);
        return Response.ok(user)
                .build();
    }

    @PUT
    @Path("/{login}")
    public Response update(@PathParam("login")String login, Candidat user){
        Long id = candidatRepo.getUserId(login);
        candidatRepo.update(id, user);
        return Response.ok(user).build();
    }
    //BodyWriter
   /* @POST
    @Path("/{login}/addFormation")
    public Response addFormation(Candidat candidat, Formation formation){
        candidatRepo.addFormation(candidat, formation);
        return Response.status(Response.Status.CREATED).build();
    }*/

    @GET
    public Response findAll(@BeanParam FilterBean filterBean) {
        List<Candidat> users = candidatRepo.findAll(Candidat.class).stream().map(element -> (Candidat)element)
                .collect(Collectors.toList());
        if(filterBean.getUserId() > 0)
            users = users.subList(filterBean.getUserId(), users.size());
        return ((users == null)?
                Response.noContent().build():
                Response.ok(users).build());
    }
}
