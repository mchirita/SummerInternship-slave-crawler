package org.iqu.webcrawler.others;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * 
 * @author Beniamin Savu
 * 
 *         Service that retrieves images, based on their ID as returned by the
 *         retrieve news/event endpoint. It should return the full binary
 *         object.
 *
 */
@Path("/images/{imageId}")
public class RetrieveImageService {

  @GET
  public Response retrieveImage(@PathParam("imageId") String imageId) {

    return Response.ok().build();
  }

}
