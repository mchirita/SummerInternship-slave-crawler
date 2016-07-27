package org.iqu.webcrawler.others;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.iqu.webcrawler.entities.ErrorMessage;
import org.iqu.webcrawler.entities.Image;

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

  private Logger LOGGER = Logger.getLogger(RetrieveImageService.class);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveImage(@PathParam("imageId") String imageId) {

    Image image = new Image();
    // ToDo select from db the proper image for the id imageId.
    image.setLink("my link");
    if (image.getLink() != null) {
      return Response.status(Status.OK).entity(image).build();
    }
    ErrorMessage errorMessage = new ErrorMessage("Image not found.");
    LOGGER.error("Image not found!");
    return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
  }

}
