package org.iqu.webcrawler.events.management;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.iqu.webcrawler.entities.Author;
import org.iqu.webcrawler.entities.Event;
import org.iqu.webcrawler.entities.Events;
import org.iqu.webcrawler.entities.Source;

@Path("/")
public class EventEndpoint {

	/**
	 * Service that will return all authors
	 * 
	 */
	@Path("/authors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveAuthors() {

		// TODO connect to the database

		Set<Author> authors = new HashSet<Author>();
		authors.add(new Author("Clark Kent"));
		authors.add(new Author("Louis Lane"));
		authors.add(new Author("Peter Parker"));
		authors.add(new Author("Ville Valo"));

		// authors.clear();

		String response = "";
		int status = 0;
		if (authors.size() > 0) {
			response = "{\"authors\" :" + authors.toString() + "}";
			status = 200;
		} else {
			response = "{\"eror\" : \"Could not fetch authors, please try again later.\"}";
			status = 404;
		}

		return Response.status(status).entity(response).build();
	}

	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retriveEvents(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
			@QueryParam("type") String type, @QueryParam("subType") String subType,
			@QueryParam("sourceId") String sourceId, @QueryParam("author") String author,
			@QueryParam("location") String location) {

		Set<Event> events = new HashSet<Event>();
		Event event1 = new Event();
		event1.setDescription("abcdef");
		event1.setSource("www.google.com");
		events.add(event1);
		Events eevents = new Events(events);

		if (startDate == null) {
			String response = "{\"error\" : \"Requested location not available\"}";
			return Response.status(200).entity(eevents).build();
		} else {
			return Response.ok().build();
		}
	}
	// TO DO : implement filter of data

	/**
	 * This method returns a list of sources where we grab our content.
	 */
	@Path("/sources")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveSource() {
		int status;
		String response = "";
		status = 0;
		Source source = new Source("1", "BNR Brasov", "This is the official BNR site");

		if (source.getDisplayName().equals("BNR Brasov")) {
			status = 200;
			return Response.status(status).entity(source).build();
		} else {
			status = 404;
			response = "\"error\" : \"Could not fetch sources, please try again later.\"";
			return Response.status(status).entity(response).build();
		}
	}

	/**
	 * This method returns all types and subtypes of events.
	 * 
	 * @return
	 */
	@Path("/types")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retriveTypes() {

		String type = "Concert";
		return Response.ok("[{\"Type\": " + "\"" + type + "\",\n\"Subtypes\" : [\"rock\", \"classical\"]}]").build();
	}

}
