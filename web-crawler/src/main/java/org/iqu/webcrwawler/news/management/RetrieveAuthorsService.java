package org.iqu.webcrwawler.news.management;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.iqu.webcrawler.entities.Author;

/**
 * 
 * @author Razvan Rosu
 * 
 *         Service that will return all authors
 *
 */

@Path("/authors")
public class RetrieveAuthorsService {

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
}
