package org.iqu.webcrawler.events.management;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.iqu.webcrawler.entities.Authors;
import org.iqu.webcrawler.entities.ErrorMessage;
import org.iqu.webcrawler.entities.Event;
import org.iqu.webcrawler.entities.Events;
import org.iqu.webcrawler.entities.Source;
import org.iqu.webcrawler.entities.Sources;
import org.iqu.webcrawler.entities.Type;
import org.iqu.webcrawler.entities.Types;

/**
 * Class that holds the services for events end-point.
 * 
 * @author Razvan Rosu
 *
 */
@Path("/")
public class EventEndpoint {

  private Logger LOGGER = Logger.getLogger(EventEndpoint.class);

  /**
   * Service that will return all authors
   * 
   */
  @Path("/authors")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveAuthors() {

    Authors authors = new Authors();
    authors.addAuthor("Clark Kent");
    authors.addAuthor("Louis Lane");
    authors.addAuthor("Peter Parker");
    authors.addAuthor("Ville Valo");

    if (authors.size() > 0) {
      return Response.status(Status.OK).entity(authors).build();
    }
    ErrorMessage errorMessage = new ErrorMessage("Could not find authors, please try again later.");
    LOGGER.error(errorMessage.getMessage());
    return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
  }

  @Path("/")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response retriveEvents(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
      @QueryParam("type") String type, @QueryParam("subType") String subType, @QueryParam("sourceId") String sourceId,
      @QueryParam("author") String author, @QueryParam("location") String location) {

    Event event1 = new Event();
    event1.setDescription("abcdef");
    event1.setSource("www.google.com");
    Events events = new Events();
    events.addEvent(event1);

    if (startDate == null) {
      ErrorMessage errorMessage = new ErrorMessage("Start Date Not Found.");
      LOGGER.error(errorMessage.getMessage());
      return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
    } else {
      return Response.ok().entity(events).build();
    }
  }
  // TO DO : implement filter of data, Search in DB

  /**
   * This method returns a list of sources where we grab our content.
   */
  @Path("/sources")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveSource() {
    Source source = new Source("1", "BNR Brasov", "This is the official BNR site",
        "http://www.inoveo.ro/inoveo/wp-content/uploads/2016/04/logo-bnr-portofoliu-simplu.jpg");

    Sources sources = new Sources();
    sources.addSource(source);

    if (!sources.isEmpty()) {

      return Response.ok().entity(sources).build();
    } else {
      ErrorMessage errorMessage = new ErrorMessage("Could not fetch sources, please try again later.");
      LOGGER.error(errorMessage.getMessage());
      return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
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

    Types types = new Types();
    Set<String> subtypes = new HashSet<String>();
    subtypes.add("Rock");
    subtypes.add("Folk");
    types.addType(new Type("music", subtypes));
    types.addType(new Type("Circ", subtypes));

    if (types.isEmpty()) {
      ErrorMessage errorMessage = new ErrorMessage("Could not fetch categories, please try again later.");
      LOGGER.error(errorMessage.getMessage());
      return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
    }
    return Response.ok().entity(types).build();
  }

}