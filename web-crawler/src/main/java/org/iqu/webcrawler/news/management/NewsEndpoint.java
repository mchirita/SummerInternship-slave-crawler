package org.iqu.webcrawler.news.management;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.iqu.webcrawler.entities.Author;
import org.iqu.webcrawler.entities.Category;
import org.iqu.webcrawler.entities.ErrorMessage;
import org.iqu.webcrawler.entities.Source;

@Path("/")
public class NewsEndpoint {

	/**
	 * Service that will return all authors
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

		String response = "";
		int status = 0;
		if (authors.size() > 0) {
			response = "{\"authors\" :" + authors.toString() + "}";
			status = 200;
		} else {
			response = "{\"error\" : \"Could not fetch authors, please try again later.\"}";
			status = 404;
		}

		return Response.status(status).entity(response).build();
	}

	/**
	 * This method implements retrieve categories service.
	 */

	@Path("/categories")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retriveCategories() {

		Set<Category> categories = new HashSet<Category>();
		// ToDo get categories from db.
		categories.add(new Category("music"));
		categories.add(new Category("music"));
		categories.add(new Category("politics"));
		categories.add(new Category("IT"));

		if (categories.isEmpty()) {
			ErrorMessage errorMessage = new ErrorMessage("Could not fetch categories, please try again later.");
			return Response.status(404).entity("{\"error\" : " + "\"" + errorMessage.getMessage() + "\"}").build();
		}

		return Response.status(200).entity("{\"categories\" : " + categories.toString() + "}").build();
	}

	/**
	 * Retrieves news based on filters, that are sent as query parameters.
	 * 
	 */
	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNews(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
			@QueryParam("categories") String categories, @QueryParam("about") String about,
			@QueryParam("sourceId") String sourceId, @QueryParam("author") String author,
			@QueryParam("location") String location) {

		String response = "";
		int status = 200;

		try {
			long startDateLong = Long.parseLong(startDate);

			// TODO: implement actual filtering of data

		} catch (NumberFormatException e) {
			status = 400;
			response = "{ \"error\" : \"startDate parameter missing/invalid\" }";
		}

		return Response.status(status).entity(response).build();
	}

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
		Set<Source> sources = new HashSet<Source>();

		// ToDo get sources from db
		sources.add(new Source("1", "BNR Brasov", "This is the official BNR site"));
		sources.add(new Source("2", "BNR Brasov", "This is the official BNR site"));
		sources.add(new Source("3", "BNR Brasov", "This is the official BNR site"));

		if (sources.isEmpty()) {
			status = 404;
			response = "\"error\" : \"Could not fetch sources, please try again later.\"";
			return Response.status(status).entity(response).build();
		}

		status = 200;
		return Response.status(status).entity("{\"sources\" : " + "\"" + sources + "\"}").build();

	}

}
