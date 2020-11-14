package org.backend.rest.formation;

import org.backend.modals.Admin;
import org.backend.modals.Personne;
import org.backend.modals.formation.Formation;
import org.backend.repository.RepositoryInt;
import org.backend.repository.formation.FormationRepoInt;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/formations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FormationResources {
    @Context
    ResourceContext context;
    @Inject
    FormationRepoInt repository;

    @GET
    public Response findAll() {
        List<Formation> formations = repository.findAll();
        return ((formations == null)?
                Response.noContent().build():
                Response.ok(formations).build());
    }

    @Path("/authenticated")
    public AuthenticatedFormations getAuthenticatedResources(){
        return this.context.getResource(AuthenticatedFormations.class);
    }
}
