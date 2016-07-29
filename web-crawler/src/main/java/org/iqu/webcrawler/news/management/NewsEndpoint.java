package org.iqu.webcrawler.news.management;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.iqu.persistence.entities.NewsArticleDTO;
import org.iqu.persistence.entities.SourceDTO;
import org.iqu.persistence.service.DAOFactory;
import org.iqu.persistence.service.NewsDAO;
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

  private static final Logger LOGGER = Logger.getLogger(NewsEndpoint.class);
  private DAOFactory daoFactory = new DAOFactory();
  private NewsDAO newsDAO = daoFactory.getNewsDAO();

  @Path("/authors")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveAuthors() {
    List<String> authorsDB = newsDAO.retrieveAuthors();
    Authors authors = new Authors();
    for (String author : authorsDB) {
      authors.addAuthor(author);
    }
    if (authors.size() > 0) {
      return Response.status(Status.OK).entity(authors).build();
    }
    ErrorMessage errorMessage = new ErrorMessage("Could not fetch categories, please try again later.");
    LOGGER.error(errorMessage.getMessage());
    return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
  }

  /**
   * This method implements retrieve categories service.
   */

  @Path("/categories")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response retriveCategories() {

    Categories categories = new Categories();
    List<String> CatDB = newsDAO.retrieveCategories();
    for (String cat : CatDB) {
      categories.addCategory(cat);
    }
    if (categories.isEmpty()) {
      ErrorMessage errorMessage = new ErrorMessage("Could not fetch categories, please try again later.");
      LOGGER.error(errorMessage.getMessage());
      return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
    }
    return Response.status(Status.OK).entity(categories).build();
  }

  /**
   * Retrieves news based on filters, that are sent as query parameters.
   * 
   */
  @Path("/")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getNews(@QueryParam("startDate") long startDate, @QueryParam("endDate") long endDate,
      @QueryParam("categories") String categories, @QueryParam("about") String about,
      @QueryParam("sourceId") int sourceId, @QueryParam("author") String author,
      @QueryParam("location") String location) {
    if (startDate == 0) {
      ErrorMessage errorMessage = new ErrorMessage("Start Date Not Found.");
      LOGGER.error(errorMessage.getMessage());
      return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
    } else {
      News news = new News();
      List<NewsArticleDTO> newsArticleDTAOs = newsDAO.retrieveNews(startDate, endDate, categories, about, sourceId,
          author);
      for (NewsArticleDTO newsArticleDTO : newsArticleDTAOs) {
        news.add(new SingleNews(newsArticleDTO.getDate(), newsArticleDTO.getId(), newsArticleDTO.getTitle(),
            newsArticleDTO.getSubtitle(), newsArticleDTO.getDescription(), newsArticleDTO.getAuthors(),
            newsArticleDTO.getCategories(), newsArticleDTO.getSource(), newsArticleDTO.getBody(),
            newsArticleDTO.getImages(), newsArticleDTO.getThumbnail_id(), newsArticleDTO.getExternal_url()));
      }
      return Response.ok(Status.OK).entity(news).build();
    }
  }

  /**
   * This method returns a list of sources where we grab our content.
   */
  @Path("/sources")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response retrieveSource() {
    Sources sources = new Sources();
    List<SourceDTO> sourceDB = newsDAO.retrieveSources();
    for (SourceDTO sourceDTO : sourceDB) {
      sources.add(
          new Source(sourceDTO.getId(), sourceDTO.getDisplayName(), sourceDTO.getDescription(), sourceDTO.getImage()));
    }
    if (sources.isEmpty()) {
      ErrorMessage errorMessage = new ErrorMessage("Could not fetch sources, please try again later.");
      LOGGER.error(errorMessage.getMessage());
      return Response.status(Status.OK).entity(errorMessage).build();
    }
    return Response.status(Status.OK).entity(sources).build();

  }

}
