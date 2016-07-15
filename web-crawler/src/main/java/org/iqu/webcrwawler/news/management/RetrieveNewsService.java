package org.iqu.webcrwawler.news.management;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Retrieves news based on filters, that are sent as query parameters.
 * 
 * @author Razvan Rosu
 *
 */
@Path("/")
public class RetrieveNewsService {

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

}
