package org.iqu.webcrwawler.news.management;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.iqu.webcrawler.entities.Categories;
import org.iqu.webcrawler.entities.ErrorMessage;

/**
 * RetrieveCategoriesService - Class that implements retrieve categories
 * service.
 * 
 * @author Razvan Rosu
 *
 */
@Path("/categories")
public class RetrieveCategoriesService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retriveCategories() {
		Categories categories = new Categories();

		// ToDo get categories from db.
		categories.addCategory("music");
		categories.addCategory("music");
		categories.addCategory("politics");
		categories.addCategory("IT");

		if (categories.isEmpty()) {
			ErrorMessage errorMessage = new ErrorMessage("Could not fetch categories, please try again later.");
			return Response.ok("{\"error\" : " + "\"" + errorMessage.getMessage() + "\"}").build();
		}

		return Response.status(200).entity("{\"categories\" : " + "\"" + categories.getCategories() + "\"}").build();
	}
}
