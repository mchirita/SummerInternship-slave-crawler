package org.iqu.persistence.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.iqu.persistence.entities.NewsArticleDTO;
import org.iqu.persistence.entities.SourceDTO;

public class NewsDAOImpl implements NewsDAO {

  private Connection connection;
  private PreparedStatement preparedStatement;
  private StringBuilder query = new StringBuilder();
  private List<Integer> ids = new ArrayList<Integer>();
  private ResultSet generatedKeys = null;
  private SourceDTO source = null;
  private static final Logger LOGGER = Logger.getLogger("NewsDaoImpl.class");

  public NewsDAOImpl() {

  }

  @Override
  public void create(NewsArticleDTO entity) {
    try {
      connectToDatabase();
      addNews(entity);
      createRelations(entity);

    } catch (SQLException e) {
      LOGGER.error("Could not insert news article into database", e);
    }
  }

  @Override
  public void addSource(SourceDTO source) {
    this.source = source;
    query.setLength(0);
    query.append("INSERT into ");
    query.append(DatabaseTables.SOURCES);
    query.append("(DisplayName, Description) ");
    query.append("values(?,?)");
    try {
      connectToDatabase();
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, source.getDisplayName());
      preparedStatement.setString(2, source.getDescription());
      preparedStatement.executeUpdate();
      generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        source.setId(generatedKeys.getInt(1));
      }
    } catch (SQLException e) {
      LOGGER.info("Source already in database thus Retrieving the Source id", e);
      query.setLength(0);
      query.append("SELECT SourceID from ");
      query.append(DatabaseTables.SOURCES);
      query.append(" where DisplayName = ?");
      try {
        preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, source.getDisplayName());
        preparedStatement.setString(2, source.getDescription());
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
          source.setId(result.getInt(1));
        }
      } catch (SQLException e1) {
        LOGGER.error("Could not fetch the Source id", e1);
      }
    }
  }

  @Override
  public void update(NewsArticleDTO entity) {
    try {
      entity.setId(getNewsId(entity));

      query.setLength(0);
      query.append("UPDATE ");
      query.append(DatabaseTables.NEWS);
      query.append(
          " SET GUID = ?, Date = ?, Title = ?, Subtitle = ?, Description = ?, Body = ?, Thumbnail_id = ?, ExternalURL = ? WHERE NewsID = ?");
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setString(1, entity.getGuid());
      preparedStatement.setLong(2, entity.getDate());
      preparedStatement.setString(3, entity.getTitle());
      preparedStatement.setString(4, entity.getSubtitle());
      preparedStatement.setString(5, entity.getDescription());
      preparedStatement.setString(6, entity.getBody());
      preparedStatement.setString(7, entity.getThumbnail_id());
      preparedStatement.setString(8, entity.getExternal_url());
      preparedStatement.setLong(9, entity.getId());
      preparedStatement.executeUpdate();

      updateRelations(entity);

      updateAuthors();
      updateImages();
      updateCategories();
      updateSources();

    } catch (SQLException e) {
      LOGGER.error("Could not update the news article", e);
    }

  }

  @Override
  public NewsArticleDTO find(NewsArticleDTO entity) {
    // TODO add filter
    return null;
  }

  @Override
  public List<NewsArticleDTO> findAll() {
    List<NewsArticleDTO> listOfNews = new ArrayList<NewsArticleDTO>();
    NewsArticleDTO newsArticle = null;
    try {
      connectToDatabase();
      query.setLength(0);
      query.append("SELECT * FROM ");
      query.append(DatabaseTables.NEWS);
      preparedStatement = connection.prepareStatement(query.toString());
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        newsArticle = new NewsArticleDTO();
        newsArticle.setGuid(result.getString("GUID"));
        long newsId = getNewsId(newsArticle);
        newsArticle.setDate(result.getLong("Date"));
        newsArticle.setTitle(result.getString("Title"));
        newsArticle.setSubtitle(result.getString("Subtitle"));
        newsArticle.setDescription(result.getString("Description"));
        newsArticle.setAuthors(findAuthorsByNewsId(newsId));
        newsArticle.setCategories(findCategoriesByNewsId(newsId));
        newsArticle.setImages(findImagesByNewsId(newsId));
        newsArticle.setBody(result.getString("Body"));
        int id = result.getInt("SourceID");
        SourceDTO source = findSourceById(id);
        newsArticle.setSource(source.getDisplayName());
        newsArticle.setThumbnail_id(result.getString("Thumbnail_id"));
        newsArticle.setExternal_url(result.getString("ExternalURL"));
        listOfNews.add(newsArticle);
      }
    } catch (SQLException e) {
      LOGGER.error("Could not update the news article", e);
    }
    return listOfNews;
  }

  @Override
  public void delete(NewsArticleDTO entity) {
    try {
      entity.setId(getNewsId(entity));
      query.setLength(0);
      query.append("DELETE FROM ");
      query.append(DatabaseTables.NEWS);
      query.append(" WHERE NewsID = ?");
      connectToDatabase();
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, entity.getId());
      preparedStatement.executeUpdate();

      updateAuthors();
      updateCategories();
      updateImages();
      updateSources();
    } catch (SQLException e) {
      LOGGER.error("Could not delete the news article", e);
    }

  }

  @Override
  public List<String> retrieveAuthors() {
    List<String> authors = new ArrayList<String>();
    String author = null;
    try {
      connectToDatabase();
      query.setLength(0);
      query.append("SELECT * FROM ");
      query.append(DatabaseTables.AUTHORS);
      preparedStatement = connection.prepareStatement(query.toString());
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        author = new String();
        author = result.getString(2);
        authors.add(author);
      }
    } catch (SQLException e) {
      LOGGER.error("Could not fetch authors from the database", e);
    }
    return authors;
  }

  @Override
  public List<SourceDTO> retrieveSources() {
    List<SourceDTO> sources = new ArrayList<SourceDTO>();
    SourceDTO source = null;
    try {
      connectToDatabase();
      query.setLength(0);
      query.append("SELECT * FROM ");
      query.append(DatabaseTables.SOURCES);
      preparedStatement = connection.prepareStatement(query.toString());
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        source = new SourceDTO();
        source.setId(result.getInt(1));
        source.setDisplayName(result.getString(2));
        source.setDescription(result.getString(3));
        sources.add(source);
      }
    } catch (SQLException e) {
      LOGGER.error("Could not fetch sources from the database", e);
    }
    return sources;
  }

  @Override
  public List<String> retrieveCategories() {
    List<String> categories = new ArrayList<String>();
    String category = null;
    try {
      connectToDatabase();
      query.setLength(0);
      query.append("SELECT * FROM ");
      query.append(DatabaseTables.CATEGORIES);
      preparedStatement = connection.prepareStatement(query.toString());
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        category = new String();
        category = result.getString(2);
        categories.add(category);
      }
    } catch (SQLException e) {
      LOGGER.error("Could not fetch categories from the database", e);
    }
    return categories;
  }

  @Override
  public List<NewsArticleDTO> findAllBySource(SourceDTO source) {
    NewsArticleDTO newsArticle = new NewsArticleDTO();
    List<NewsArticleDTO> result = new ArrayList<NewsArticleDTO>();

    try {
      connectToDatabase();
      query.setLength(0);
      query.append("SELECT SourceID FROM ");
      query.append(DatabaseTables.SOURCES);
      query.append(" where DisplayName = ? and Description = ?");
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setString(1, source.getDisplayName());
      preparedStatement.setString(2, source.getDescription());
      ResultSet res = preparedStatement.executeQuery();
      if (res.next()) {
        source.setId(res.getInt(1));
      }

      query.setLength(0);
      query.append("SELECT * FROM ");
      query.append(DatabaseTables.NEWS);
      query.append(" where SourceID = ?");
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setInt(1, source.getId());
      ResultSet rs = preparedStatement.executeQuery(query.toString());
      while (rs.next()) {
        newsArticle.setGuid(rs.getString("NewsID"));
        newsArticle.setDate(rs.getInt("Date"));
        newsArticle.setTitle(rs.getString("Title"));
        newsArticle.setSubtitle(rs.getString("Subtitle"));
        newsArticle.setDescription(rs.getString("Description"));
        newsArticle.setSource(source.getDisplayName());
        newsArticle.setBody(rs.getString("Body"));
        result.add(newsArticle);
      }
    } catch (SQLException e) {
      LOGGER.error("Could not fetch news from the database", e);
    }

    return result;
  }

  private List<String> findImagesByNewsId(long newsId) {
    List<String> images = new ArrayList<String>();
    String image = null;
    try {
      query.setLength(0);
      query.append("SELECT URL FROM ");
      query.append(DatabaseTables.IMAGES);
      query.append(" JOIN ");
      query.append(DatabaseTables.NEWS_HAS_IMAGES);
      query.append(" ON ");
      query.append(DatabaseTables.IMAGES);
      query.append(".ImageID = ");
      query.append(DatabaseTables.NEWS_HAS_IMAGES);
      query.append(".ImageID");
      query.append(" WHERE ");
      query.append(DatabaseTables.NEWS_HAS_IMAGES);
      query.append(".NewsId = ?");
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, newsId);
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        image = new String();
        image = result.getString("URL");
        images.add(image);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return images;
  }

  private List<String> findCategoriesByNewsId(long newsId) {
    List<String> categories = new ArrayList<String>();
    String category = null;
    try {
      query.setLength(0);
      query.append("SELECT CategoryName FROM ");
      query.append(DatabaseTables.CATEGORIES);
      query.append(" JOIN ");
      query.append(DatabaseTables.NEWS_HAS_CATEGORIES);
      query.append(" ON ");
      query.append(DatabaseTables.CATEGORIES);
      query.append(".CategoryID = ");
      query.append(DatabaseTables.NEWS_HAS_CATEGORIES);
      query.append(".CategoryID");
      query.append(" WHERE ");
      query.append(DatabaseTables.NEWS_HAS_CATEGORIES);
      query.append(".NewsId = ?");
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, newsId);
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        category = new String();
        category = result.getString("CategoryName");
        categories.add(category);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return categories;
  }

  private List<String> findAuthorsByNewsId(long newsId) {
    List<String> authors = new ArrayList<String>();
    String author = null;
    try {
      query.setLength(0);
      query.append("SELECT AuthorName FROM ");
      query.append(DatabaseTables.AUTHORS);
      query.append(" JOIN ");
      query.append(DatabaseTables.NEWS_HAS_AUTHORS);
      query.append(" ON ");
      query.append(DatabaseTables.AUTHORS);
      query.append(".AuthorID = ");
      query.append(DatabaseTables.NEWS_HAS_AUTHORS);
      query.append(".AuthorID");
      query.append(" WHERE ");
      query.append(DatabaseTables.NEWS_HAS_AUTHORS);
      query.append(".NewsId = ?");
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, newsId);
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        author = new String();
        author = result.getString("AuthorName");
        authors.add(author);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return authors;
  }

  private void connectToDatabase() throws SQLException {

    if (connection == null) {
      connection = DAOFactory.createConnection();
    }
  }

  private void addNews(NewsArticleDTO entity) throws SQLException {
    query.setLength(0);
    query.append("INSERT into ");
    query.append(DatabaseTables.NEWS);
    query.append("(GUID, Date, Title, Subtitle, Description, Body, SourceID, Thumbnail_id, ExternalURL) ");
    query.append("values(?,?,?,?,?,?,?,?,?)");

    preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
    preparedStatement.setString(1, entity.getGuid());
    preparedStatement.setLong(2, entity.getDate());
    preparedStatement.setString(3, entity.getTitle());
    preparedStatement.setString(4, entity.getSubtitle());
    preparedStatement.setString(5, entity.getDescription());
    preparedStatement.setString(6, entity.getBody());
    preparedStatement.setInt(7, source.getId());
    preparedStatement.setString(8, entity.getThumbnail_id());
    preparedStatement.setString(9, entity.getExternal_url());
    preparedStatement.executeUpdate();
    generatedKeys = preparedStatement.getGeneratedKeys();
    if (generatedKeys.next()) {
      entity.setId(generatedKeys.getInt(1));
    }

  }

  private void addNewsAndImageRelations(NewsArticleDTO entity) {
    query.setLength(0);
    query.append("INSERT into ");
    query.append(DatabaseTables.NEWS_HAS_IMAGES);
    query.append("(NewsID, ImageID) ");
    query.append("values(?,?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, entity.getId());
      for (Integer id : ids) {
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      LOGGER.info("Relations already exist", e);
    }
    ids.clear();

  }

  private void addImage(String image) {
    query.setLength(0);
    query.append("INSERT into ");
    query.append(DatabaseTables.IMAGES);
    query.append("(URL) ");
    query.append("values(?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, image);
      preparedStatement.executeUpdate();
      generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        ids.add(generatedKeys.getInt(1));
      }
    } catch (SQLException e) {
      LOGGER.info("Source already in database thus Retrieving the Image id", e);
      query.setLength(0);
      query.append("SELECT ImageID FROM ");
      query.append(DatabaseTables.IMAGES);
      query.append(" WHERE URL = ?");
      try {
        preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, image);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
          ids.add(result.getInt(1));
        }
      } catch (SQLException e1) {
        LOGGER.error("Could not fetch the Image id", e1);
      }
    }

  }

  private void addNewsAndCategoryRelations(NewsArticleDTO entity) {
    query.setLength(0);
    query.append("INSERT into ");
    query.append(DatabaseTables.NEWS_HAS_CATEGORIES);
    query.append("(NewsID, CategoryID) ");
    query.append("values(?,?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, entity.getId());
      for (Integer id : ids) {
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      LOGGER.info("Relations already exist", e);
    }
    ids.clear();

  }

  private void addCategory(String category) {
    query.setLength(0);
    query.append("INSERT into ");
    query.append(DatabaseTables.CATEGORIES);
    query.append("(CategoryName) ");
    query.append("values(?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, category);
      preparedStatement.executeUpdate();
      generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        ids.add(generatedKeys.getInt(1));
      }
    } catch (SQLException e) {
      LOGGER.info("Source already in database thus Retrieving the Category id", e);
      query.setLength(0);
      query.append("SELECT CategoryID FROM ");
      query.append(DatabaseTables.CATEGORIES);
      query.append(" where CategoryName = ?");
      try {
        preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, category);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
          ids.add(result.getInt(1));
        }
      } catch (SQLException e1) {
        LOGGER.error("Could not fetch the Category id", e1);
      }
    }
  }

  private void addNewsAndAuthorRelations(NewsArticleDTO entity) {
    query.setLength(0);
    query.append("INSERT into ");
    query.append(DatabaseTables.NEWS_HAS_AUTHORS);
    query.append("(NewsID, AuthorID) ");
    query.append("values(?,?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, entity.getId());
      for (Integer id : ids) {
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      LOGGER.info("Relations already exist", e);
    }
    ids.clear();
  }

  private void addAuthor(String author) {
    query.setLength(0);
    query.append("INSERT into ");
    query.append(DatabaseTables.AUTHORS);
    query.append("(AuthorName) ");
    query.append("values(?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, author);
      preparedStatement.executeUpdate();
      generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        ids.add(generatedKeys.getInt(1));
      }
    } catch (SQLException e) {
      LOGGER.info("Source already in database thus Retrieving the Author id", e);
      query.setLength(0);
      query.append("SELECT AuthorID FROM ");
      query.append(DatabaseTables.AUTHORS);
      query.append(" where AuthorName = ?");
      try {
        preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, author);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
          ids.add(result.getInt(1));
        }
      } catch (SQLException e1) {
        LOGGER.error("Could not fetch the Author id", e1);
      }
    }
  }

  private void createRelations(NewsArticleDTO entity) {
    for (String author : entity.getAuthors()) {
      addAuthor(author);
    }
    addNewsAndAuthorRelations(entity);

    for (String category : entity.getCategories()) {
      addCategory(category);
    }
    addNewsAndCategoryRelations(entity);

    for (String image : entity.getImages()) {
      addImage(image);
    }
    addNewsAndImageRelations(entity);
  }

  private void updateRelations(NewsArticleDTO entity) throws SQLException {
    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.NEWS_HAS_AUTHORS);
    query.append(" where NewsID = ?");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.setLong(1, entity.getId());
    preparedStatement.executeUpdate();

    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.NEWS_HAS_CATEGORIES);
    query.append(" where NewsID = ?");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.setLong(1, entity.getId());
    preparedStatement.executeUpdate();

    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.NEWS_HAS_IMAGES);
    query.append(" where NewsID = ?");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.setLong(1, entity.getId());
    preparedStatement.executeUpdate();

    createRelations(entity);
  }

  private void updateAuthors() throws SQLException {
    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.AUTHORS);
    query.append(" WHERE NOT EXISTS ( SELECT 1 FROM ");
    query.append(DatabaseTables.NEWS_HAS_AUTHORS);
    query.append(" WHERE ");
    query.append(DatabaseTables.AUTHORS);
    query.append(".AuthorID=");
    query.append(DatabaseTables.NEWS_HAS_AUTHORS);
    query.append(".AuthorID)");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.executeUpdate();
  }

  private void updateCategories() throws SQLException {
    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.CATEGORIES);
    query.append(" WHERE NOT EXISTS ( SELECT 1 FROM ");
    query.append(DatabaseTables.NEWS_HAS_CATEGORIES);
    query.append(" WHERE ");
    query.append(DatabaseTables.CATEGORIES);
    query.append(".CategoryID=");
    query.append(DatabaseTables.NEWS_HAS_CATEGORIES);
    query.append(".CategoryID)");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.executeUpdate();
  }

  private void updateImages() throws SQLException {
    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.IMAGES);
    query.append(" WHERE NOT EXISTS ( SELECT 1 FROM ");
    query.append(DatabaseTables.NEWS_HAS_IMAGES);
    query.append(" WHERE ");
    query.append(DatabaseTables.IMAGES);
    query.append(".ImageID=");
    query.append(DatabaseTables.NEWS_HAS_IMAGES);
    query.append(".ImageID)");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.executeUpdate();
  }

  private void updateSources() throws SQLException {
    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.SOURCES);
    query.append(" WHERE NOT EXISTS ( SELECT 1 FROM ");
    query.append(DatabaseTables.NEWS);
    query.append(" WHERE ");
    query.append(DatabaseTables.SOURCES);
    query.append(".SourceID=");
    query.append(DatabaseTables.NEWS);
    query.append(".SourceID)");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.executeUpdate();
  }

  private SourceDTO findSourceById(int id) throws SQLException {
    query.setLength(0);
    query.append("SELECT * FROM ");
    query.append(DatabaseTables.SOURCES);
    query.append(" WHERE SourceID = ?");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.setInt(1, id);
    ResultSet sourceResult = preparedStatement.executeQuery();
    SourceDTO source = new SourceDTO();
    if (sourceResult.next()) {
      source.setId(sourceResult.getInt(1));
      source.setDisplayName(sourceResult.getString(2));
      source.setDescription(sourceResult.getString(3));
    }
    return source;
  }

  private long getNewsId(NewsArticleDTO entity) throws SQLException {
    connectToDatabase();
    long newsId = 0;
    query.setLength(0);
    query.append("SELECT NewsID FROM ");
    query.append(DatabaseTables.NEWS);
    query.append(" WHERE GUID = ?");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.setString(1, entity.getGuid());
    ResultSet res = preparedStatement.executeQuery();
    if (res.next()) {
      newsId = res.getLong(1);
    }

    return newsId;
  }
}
