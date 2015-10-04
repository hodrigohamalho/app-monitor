package lab.hack.appmonitor.rest;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lab.hack.appmonitor.model.Application;
import lab.hack.appmonitor.persitence.ApplicationDAO;

@Path("/apps")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationREST {
	
	@Inject
	private Logger log;
	@Inject
	ApplicationDAO appDAO;
	
	@GET
	public List<Application> listAllMembers() {
		return appDAO.findAll();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Application findById(@PathParam("id") long id) {
		Application app = appDAO.findById(id);
		
		if (app == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		return app;
	}
	
	@POST
	public Response save(Application app){
		if (app == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		
		appDAO.save(app);
		log.info("App: "+app.getContext() + " created!");
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response remove(@PathParam("id") Long id){
		appDAO.remove(id);
		log.info("App with id: "+id+" removed!");
		return Response.ok().build();
	}
	
	@PUT
	public Response update(Application app){
		appDAO.update(app);
		log.info("App: "+app.getContext() + " updated!");
		return Response.ok().build();
	}
}
