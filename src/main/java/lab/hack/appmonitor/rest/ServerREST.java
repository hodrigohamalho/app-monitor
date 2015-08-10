package lab.hack.appmonitor.rest;

import java.util.List;

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

import lab.hack.appmonitor.model.Server;
import lab.hack.appmonitor.persitence.ServerDAO;

@Path("/servers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServerREST {

	@Inject
	ServerDAO serverDAO;
	
	@GET
	public List<Server> listAllMembers() {
		return serverDAO.findAll();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Server findById(@PathParam("id") long id) {
		Server server = serverDAO.findById(id);
		
		if (server == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		return server;
	}
	
	@POST
	public Response save(Server server){
		serverDAO.save(server);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response remove(@PathParam("id") Long id){
		serverDAO.remove(id);
		return Response.ok().build();
	}
	
	@PUT
	public Response update(Server server){
		serverDAO.update(server);
		return Response.ok().build();
	}
	
}
