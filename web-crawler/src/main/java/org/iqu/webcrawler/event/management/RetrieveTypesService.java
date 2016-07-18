package org.iqu.webcrawler.event.management;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class returns all types and subtypes of events.
 * 
 * @author Razvan Rosu
 *
 */
@Path("/types")
public class RetrieveTypesService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retriveTypes() {

		String type = "Concert";
		return Response.ok("[{\"Type\": " + "\"" + type + "\",\n\"Subtypes\" : [\"rock\", \"classical\"]}]").build();
	}

}
