package org.iqu.webcrawler.news.management;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.iqu.webcrawler.entities.Authors;
import org.iqu.webcrawler.entities.Categories;
import org.iqu.webcrawler.entities.ErrorMessage;
import org.iqu.webcrawler.entities.News;
import org.iqu.webcrawler.entities.SingleNews;
import org.iqu.webcrawler.entities.Source;
import org.iqu.webcrawler.entities.Sources;

@Path("/")
public class NewsEndpoint {

	/**
	 * Service that will return all authors
	 */

	private Logger LOGGER = Logger.getLogger(NewsEndpoint.class);

	@Path("/authors")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveAuthors() {

		// TODO connect to the database

		Authors authors = new Authors();
		authors.addAuthor("Clark Kent");
		authors.addAuthor("Louis Lane");
		authors.addAuthor("Peter Parker");
		authors.addAuthor("Ville Valo");

		int status = 0;
		if (authors.size() > 0) {
			status = 200;
			return Response.status(status).entity(authors).build();
		}
		status = 404;
		ErrorMessage errorMessage = new ErrorMessage("Could not fetch categories, please try again later.");
		return Response.status(status).entity(errorMessage).build();
	}

	/**
	 * This method implements retrieve categories service.
	 */

	@Path("/categories")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retriveCategories() {

		Categories categories = new Categories();
		categories.addCategory("music");
		categories.addCategory("music");
		categories.addCategory("IT");
		categories.addCategory("politics");

		if (categories.isEmpty()) {
			ErrorMessage errorMessage = new ErrorMessage("Could not fetch categories, please try again later.");
			return Response.status(404).entity(errorMessage).build();
		}
		return Response.status(200).entity(categories).build();
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

		News news = new News();
		SingleNews singleNews1 = new SingleNews();
		singleNews1.setDescription("abcdef");
		singleNews1.setSource("www.google.com");
		singleNews1.setDate("12345");
		news.add(singleNews1);

		if (startDate == null) {
			ErrorMessage errorMessage = new ErrorMessage("Could not find location, please try again later.");
			return Response.status(200).entity(news).build();
		} else {
			return Response.ok().build();
		}
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
		Sources sources = new Sources();

		// ToDo get sources from db
		sources.add(new Source("1", "BNR Brasov", "This is the official BNR site"));
		sources.add(new Source("2", "BNR Brasov", "This is the official BNR site"));
		sources.add(new Source("3", "BNR Brasov", "This is the official BNR site"));

		if (sources.isEmpty()) {
			status = 404;
			ErrorMessage errorMessage = new ErrorMessage("Could not fetch sources, please try again later.");
			return Response.status(status).entity(errorMessage).build();
		}

		status = 200;
		return Response.status(status).entity(sources).build();

	}

}
