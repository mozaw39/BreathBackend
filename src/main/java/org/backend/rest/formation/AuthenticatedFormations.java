package org.backend.rest.formation;

import org.backend.modals.formation.Formation;
import org.backend.repository.formation.FormationRepoInt;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticatedFormations {
    @Inject
    FormationRepoInt repository;

    @POST
    public Response create(Formation inscrit, @Context UriInfo uriInfo) {
        Formation formation = repository.create(inscrit);
        URI uriForUser = getUrlForFormation(formation, uriInfo);
        //ADD LINKS
        //user.addLink(uriForUser.toString(), "self");
        //user = repository.update(user.getUserId(), user);
        return Response.created(uriForUser).entity(formation).build();
    }



    @DELETE
    @Path("/{formationId}")
    public Response delete(@PathParam("formationId")Long id) {
        repository.delete(id);
        return Response.noContent().build();
    }
    @GET
    @Path("/{formationId}")
    public Response find(@PathParam("formationId")Long id) {
        Formation formation = repository.find(id);
        return Response.ok(formation)
                .build();
    }
    private URI getUrlForFormation(Formation formation, UriInfo uriInfo) {
        URI uri = uriInfo.getAbsolutePathBuilder().path(formation.getFormationId().toString()).build();
        return uri;
    }
}
