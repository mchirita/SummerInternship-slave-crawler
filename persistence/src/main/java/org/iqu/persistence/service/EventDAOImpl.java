package org.iqu.persistence.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.iqu.persistence.entities.EventDTO;
import org.iqu.persistence.entities.SourceDTO;
import org.iqu.persistence.entities.TypeDTO;

public class EventDAOImpl implements EventDAO {

  private Connection connection;
  private PreparedStatement preparedStatement;
  private StringBuilder query = new StringBuilder();
  private List<Integer> ids = new ArrayList<Integer>();
  private ResultSet generatedKeys = null;
  private SourceDTO source = null;
  private TypeDTO type = new TypeDTO();
  private static final Logger LOGGER = Logger.getLogger("EventDAOImpl.class");

  @Override
  public void create(EventDTO entity) {
    try {
      connectToDatabase();
      addTypes(entity);
      addEvent(entity);
      createRelations(entity);
    } catch (SQLException e) {
      LOGGER.error("Could not insert event into database", e);
    }
  }

  @Override
  public void update(EventDTO entity) {
    try {
      entity.setId(getEventId(entity));
      addTypes(entity);
      query.setLength(0);
      query.append("UPDATE ");
      query.append(DatabaseTables.EVENTS);
      query.append(
          " SET StartDate = ?, EndDate = ?, Title = ?, Subtitle = ?, Description = ?, TypeID = ?, Body = ?, Thumbnail_id = ?, ExternalURL = ? WHERE EventID = ?");
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, entity.getStartDate());
      preparedStatement.setLong(2, entity.getEndDate());
      preparedStatement.setString(3, entity.getTitle());
      preparedStatement.setString(4, entity.getSubtitle());
      preparedStatement.setString(5, entity.getDescription());
      preparedStatement.setLong(6, type.getId());
      preparedStatement.setString(7, entity.getBody());
      preparedStatement.setString(8, entity.getThumbnail_id());
      preparedStatement.setString(9, entity.getExternal_url());
      preparedStatement.setLong(10, entity.getId());
      preparedStatement.executeUpdate();

      updateRelations(entity);

      updateAuthors();
      updateTypes();
      updateImages();
      updateSources();
    } catch (SQLException e) {
      LOGGER.error("Could not update the news article", e);
      e.printStackTrace();
    }

  }

  @Override
  public EventDTO find(EventDTO entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<EventDTO> findAll() {
    List<EventDTO> listOfEvents = new ArrayList<EventDTO>();
    EventDTO event = null;
    try {
      connectToDatabase();
      query.setLength(0);
      query.append("SELECT * FROM ");
      query.append(DatabaseTables.EVENTS);
      preparedStatement = connection.prepareStatement(query.toString());
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        event = new EventDTO();
        event.setStartDate(result.getLong("StartDate"));
        event.setEndDate(result.getLong("EndDate"));
        event.setExternal_url(result.getString("ExternalURL"));
        long eventId = getEventId(event);
        event.setTitle(result.getString("Title"));
        event.setSubtitle(result.getString("Subtitle"));
        event.setDescription(result.getString("Description"));
        event.setAuthors(findAuthorsByEventId(eventId));
        event.setImages(findImagesByEventId(eventId));
        event.setBody(result.getString("Body"));
        int id = result.getInt("SourceID");
        SourceDTO source = findSourceById(id);
        id = result.getInt("TypeID");
        TypeDTO type = findTypeSubtypesById(id);
        event.setType(type.getType());
        event.setSubtypes(type.getSubtypes());
        event.setSource(source.getDisplayName());
        event.setThumbnail_id(result.getString("Thumbnail_id"));
        listOfEvents.add(event);
      }
    } catch (SQLException e) {
      LOGGER.error("Could not retrieve the event article", e);
    }
    return listOfEvents;
  }

  @Override
  public void delete(EventDTO entity) {
    try {
      entity.setId(getEventId(entity));
      query.setLength(0);
      query.append("DELETE FROM ");
      query.append(DatabaseTables.EVENTS);
      query.append(" WHERE EventID = ?");
      connectToDatabase();
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, entity.getId());
      preparedStatement.executeUpdate();

      updateAuthors();
      updateTypes();
      updateImages();
      updateSources();
    } catch (SQLException e) {
      LOGGER.error("Could not delete the event article", e);
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
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
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
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
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
  public List<TypeDTO> retrieveTypesAndSubtypes() {
    List<TypeDTO> types = new ArrayList<TypeDTO>();
    TypeDTO type = null;
    try {
      connectToDatabase();
      query.setLength(0);
      query.append("SELECT * FROM ");
      query.append(DatabaseTables.TYPES);
      preparedStatement = connection.prepareStatement(query.toString());
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        type = new TypeDTO();
        type.setType(result.getString("TypeName"));
        type.setSubtypes(getSubtypesByTypeId(result.getLong("TypeID")));
        types.add(type);
      }
    } catch (SQLException e) {
      LOGGER.error("Could not fetch types/subtypes from the database", e);
    }
    return types;
  }

  @Override
  public List<EventDTO> findAllBySource(SourceDTO source) {
    EventDTO event = null;
    List<EventDTO> result = new ArrayList<EventDTO>();

    try {
      connectToDatabase();
      query.setLength(0);
      query.append("SELECT SourceID FROM ");
      query.append(DatabaseTables.SOURCES);
      query.append(" where DisplayName = ? and Description = ?");
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, source.getDisplayName());
      preparedStatement.setString(2, source.getDescription());
      ResultSet res = preparedStatement.executeQuery();
      if (res.next()) {
        source.setId(res.getInt(1));
      }

      query.setLength(0);
      query.append("SELECT * FROM ");
      query.append(DatabaseTables.EVENTS);
      query.append(" where SourceID = ?");
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setInt(1, source.getId());
      ResultSet rs = preparedStatement.executeQuery(query.toString());
      while (rs.next()) {
        event = new EventDTO();
        event.setStartDate(rs.getLong("StartDate"));
        event.setEndDate(rs.getLong("EndDate"));
        event.setTitle(rs.getString("Title"));
        event.setSubtitle(rs.getString("Subtitle"));
        event.setDescription(rs.getString("Description"));

        event.setSource(source.getDisplayName());
        result.add(event);
      }
    } catch (SQLException e) {
      LOGGER.error("Could not fetch news from the database", e);
    }

    return result;
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
      LOGGER.info("Source already in database thus Retrieving the Source id");
      query.setLength(0);
      query.append("SELECT SourceID FROM ");
      query.append(DatabaseTables.SOURCES);
      query.append(" where DisplayName = ?");
      try {
        preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, source.getDisplayName());
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
          source.setId(result.getInt(1));
        }
      } catch (SQLException e1) {
        LOGGER.error("Could not fetch the Source id", e1);
      }
    }

  }

  private List<String> getSubtypesByTypeId(long id) {
    List<String> subtypes = new ArrayList<String>();
    String subtype = null;
    query.setLength(0);
    query.append("SELECT SubtypeName FROM ");
    query.append(DatabaseTables.SUBTYPES);
    query.append(" JOIN ");
    query.append(DatabaseTables.TYPES_HAS_SUBTYPES);
    query.append(" ON ");
    query.append(DatabaseTables.SUBTYPES);
    query.append(".SubtypeID = ");
    query.append(DatabaseTables.TYPES_HAS_SUBTYPES);
    query.append(".SubtypeID");
    query.append(" WHERE ");
    query.append(DatabaseTables.TYPES_HAS_SUBTYPES);
    query.append(".TypeID = ?");
    try {
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, id);
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        subtype = new String();
        subtype = result.getString("SubtypeName");
        subtypes.add(subtype);
      }
    } catch (SQLException e) {
      LOGGER.error("Could not get Subtypes by the specidied id", e);
    }

    return subtypes;
  }

  private void addEvent(EventDTO entity) throws SQLException {
    query.setLength(0);
    query.append("INSERT into ");
    query.append(DatabaseTables.EVENTS);
    query.append(
        "(StartDate, EndDate, Title, Subtitle, Description, SourceID, TypeID, Body, Thumbnail_id, ExternalURL) ");
    query.append("values(?,?,?,?,?,?,?,?,?,?)");
    preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
    preparedStatement.setLong(1, entity.getStartDate());
    preparedStatement.setLong(2, entity.getEndDate());
    preparedStatement.setString(3, entity.getTitle());
    preparedStatement.setString(4, entity.getSubtitle());
    preparedStatement.setString(5, entity.getDescription());
    preparedStatement.setInt(6, source.getId());
    preparedStatement.setLong(7, type.getId());
    preparedStatement.setString(8, entity.getBody());
    preparedStatement.setString(9, entity.getThumbnail_id());
    preparedStatement.setString(10, entity.getExternal_url());
    preparedStatement.executeUpdate();
    generatedKeys = preparedStatement.getGeneratedKeys();
    if (generatedKeys.next()) {
      entity.setId(generatedKeys.getInt(1));
    }
  }

  private void connectToDatabase() throws SQLException {

    if (connection == null) {
      connection = DAOFactory.createConnection();
    }
  }

  private void addEventAndImageRelations(EventDTO entity) {
    query.setLength(0);
    query.append("INSERT INTO ");
    query.append(DatabaseTables.EVENTS_HAS_IMAGES);
    query.append("(EventID, ImageID) values(?,?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setLong(1, entity.getId());
      for (Integer id : ids) {
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      LOGGER.info("Relation already exists");
    }
    ids.clear();

  }

  private void addImage(String image) {
    query.setLength(0);
    query.append("INSERT INTO ");
    query.append(DatabaseTables.IMAGES);
    query.append("(URL)");
    query.append(" values(?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, image);
      preparedStatement.executeUpdate();
      generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        ids.add(generatedKeys.getInt(1));
      }
    } catch (SQLException e) {
      LOGGER.info("Image already in database thus Retrieving the Image id");
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

  private void addEventAndAuthorRelations(EventDTO entity) {
    query.setLength(0);
    query.append("INSERT INTO ");
    query.append(DatabaseTables.EVENTS_HAS_AUTHORS);
    query.append(" values(?,?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, entity.getId());
      for (Integer id : ids) {
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      LOGGER.info("Relation already exists", e);
    }
    ids.clear();
  }

  private void addAuthor(String author) {
    query.setLength(0);
    query.append("INSERT INTO ");
    query.append(DatabaseTables.AUTHORS);
    query.append("(AuthorName)");
    query.append(" values(?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, author);
      preparedStatement.executeUpdate();
      generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        ids.add(generatedKeys.getInt(1));
      }
    } catch (SQLException e) {
      LOGGER.info("Author already in database thus Author the Image id");
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

  private void addSubtype(String subtype) {
    query.setLength(0);
    query.append("INSERT INTO ");
    query.append(DatabaseTables.SUBTYPES);
    query.append("(SubtypeName)");
    query.append(" values(?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, subtype);
      preparedStatement.executeUpdate();
      generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        ids.add(generatedKeys.getInt(1));
      }
    } catch (SQLException e) {
      LOGGER.info("Subtype already in database thus Retrieving the Subtype id");
      query.setLength(0);
      query.append("SELECT SubtypeID FROM ");
      query.append(DatabaseTables.SUBTYPES);
      query.append(" WHERE SubtypeName = ?");
      try {
        preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, subtype);
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
          ids.add(result.getInt(1));
        }
      } catch (SQLException e1) {
        LOGGER.error("Could not fetch the Subtye id", e1);
      }
    }

  }

  private void createRelations(EventDTO entity) {
    for (String author : entity.getAuthors()) {
      addAuthor(author);
    }
    addEventAndAuthorRelations(entity);

    for (String subtype : entity.getSubtypes()) {
      addSubtype(subtype);
    }
    addTypeAndSubtypeRelations(type);

    for (String image : entity.getImages()) {
      addImage(image);
    }
    addEventAndImageRelations(entity);
  }

  private void addTypeAndSubtypeRelations(TypeDTO type) {
    query.setLength(0);
    query.append("INSERT INTO ");
    query.append(DatabaseTables.TYPES_HAS_SUBTYPES);
    query.append("(TypeID, SubtypeID) values(?,?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, type.getId());
      for (Integer id : ids) {
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      LOGGER.info("Relation already exists");
    }
    ids.clear();
  }

  private void addTypes(EventDTO entity) {
    query.setLength(0);
    query.append("INSERT INTO ");
    query.append(DatabaseTables.TYPES);
    query.append("(TypeName) ");
    query.append("values(?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, entity.getType());
      preparedStatement.executeUpdate();
      generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        type.setId(generatedKeys.getLong(1));
      }
    } catch (SQLException e) {
      LOGGER.info("Type already in database thus Retrieving the Type id");
      query.setLength(0);
      query.append("SELECT TypeID from ");
      query.append(DatabaseTables.TYPES);
      query.append(" where TypeName = ?");
      try {
        preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, entity.getType());
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
          type.setId(result.getLong(1));
        }
      } catch (SQLException e1) {
        LOGGER.error("Could not fetch the Type id", e1);
      }
    }
  }

  private void updateTypes() throws SQLException {
    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.TYPES);
    query.append(" WHERE NOT EXISTS ( SELECT 1 FROM ");
    query.append(DatabaseTables.EVENTS);
    query.append(" WHERE ");
    query.append(DatabaseTables.TYPES);
    query.append(".TypeID=");
    query.append(DatabaseTables.EVENTS);
    query.append(".TypeID)");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.executeUpdate();
    updateSubtypes();
  }

  private void updateSubtypes() throws SQLException {
    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.SUBTYPES);
    query.append(" WHERE NOT EXISTS ( SELECT 1 FROM ");
    query.append(DatabaseTables.TYPES_HAS_SUBTYPES);
    query.append(" WHERE ");
    query.append(DatabaseTables.SUBTYPES);
    query.append(".SubtypeID=");
    query.append(DatabaseTables.TYPES_HAS_SUBTYPES);
    query.append(".SubtypeID)");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.executeUpdate();

  }

  private void updateSources() throws SQLException {
    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.SOURCES);
    query.append(" WHERE NOT EXISTS ( SELECT 1 FROM ");
    query.append(DatabaseTables.EVENTS);
    query.append(" WHERE ");
    query.append(DatabaseTables.SOURCES);
    query.append(".SourceID=");
    query.append(DatabaseTables.EVENTS);
    query.append(".SourceID)");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.executeUpdate();
  }

  private void updateImages() throws SQLException {
    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.IMAGES);
    query.append(" WHERE NOT EXISTS ( SELECT 1 FROM ");
    query.append(DatabaseTables.EVENTS_HAS_IMAGES);
    query.append(" WHERE ");
    query.append(DatabaseTables.IMAGES);
    query.append(".ImageID=");
    query.append(DatabaseTables.EVENTS_HAS_IMAGES);
    query.append(".ImageID)");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.executeUpdate();

  }

  private void updateAuthors() throws SQLException {
    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.AUTHORS);
    query.append(" WHERE NOT EXISTS ( SELECT 1 FROM ");
    query.append(DatabaseTables.EVENTS_HAS_AUTHORS);
    query.append(" WHERE ");
    query.append(DatabaseTables.AUTHORS);
    query.append(".AuthorID=");
    query.append(DatabaseTables.EVENTS_HAS_AUTHORS);
    query.append(".AuthorID)");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.executeUpdate();

  }

  private void updateRelations(EventDTO entity) throws SQLException {
    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.EVENTS_HAS_AUTHORS);
    query.append(" where EventID = ?");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.setLong(1, entity.getId());
    preparedStatement.executeUpdate();

    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.EVENTS_HAS_IMAGES);
    query.append(" where EventID = ?");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.setLong(1, entity.getId());
    preparedStatement.executeUpdate();

    query.setLength(0);
    query.append("DELETE FROM ");
    query.append(DatabaseTables.TYPES_HAS_SUBTYPES);
    query.append(" where TypeID = ?");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.setLong(1, type.getId());
    preparedStatement.executeUpdate();

    createRelations(entity);

  }

  private long getEventId(EventDTO entity) throws SQLException {
    connectToDatabase();
    long eventId = 0;
    query.setLength(0);
    query.append("SELECT EventID FROM ");
    query.append(DatabaseTables.EVENTS);
    query.append(" WHERE ExternalURL = ?");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.setString(1, entity.getExternal_url());
    ResultSet res = preparedStatement.executeQuery();
    if (res.next()) {
      eventId = res.getLong(1);
    }

    return eventId;
  }

  private TypeDTO findTypeSubtypesById(int id) throws SQLException {
    TypeDTO type = new TypeDTO();
    query.setLength(0);
    query.append("SELECT TypeName FROM ");
    query.append(DatabaseTables.TYPES);
    query.append(" WHERE TypeID = ?");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.setInt(1, id);
    ResultSet result = preparedStatement.executeQuery();
    while (result.next()) {
      type.setType(result.getString("TypeName"));
    }
    type.setSubtypes(getSubtypesByTypeId(id));
    return type;
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

  private List<String> findImagesByEventId(long eventId) {
    List<String> images = new ArrayList<String>();
    String image = null;
    try {
      query.setLength(0);
      query.append("SELECT URL FROM ");
      query.append(DatabaseTables.IMAGES);
      query.append(" JOIN ");
      query.append(DatabaseTables.EVENTS_HAS_IMAGES);
      query.append(" ON ");
      query.append(DatabaseTables.IMAGES);
      query.append(".ImageID = ");
      query.append(DatabaseTables.EVENTS_HAS_IMAGES);
      query.append(".ImageID");
      query.append(" WHERE ");
      query.append(DatabaseTables.EVENTS_HAS_IMAGES);
      query.append(".EventID = ?");
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, eventId);
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        image = new String();
        image = result.getString("URL");
        images.add(image);
      }
    } catch (SQLException e) {
      LOGGER.error("Could not retrieve images from the database", e);
    }

    return images;
  }

  private List<String> findAuthorsByEventId(long eventId) {
    List<String> authors = new ArrayList<String>();
    String author = null;
    try {
      query.setLength(0);
      query.append("SELECT AuthorName FROM ");
      query.append(DatabaseTables.AUTHORS);
      query.append(" JOIN ");
      query.append(DatabaseTables.EVENTS_HAS_AUTHORS);
      query.append(" ON ");
      query.append(DatabaseTables.AUTHORS);
      query.append(".AuthorID = ");
      query.append(DatabaseTables.EVENTS_HAS_AUTHORS);
      query.append(".AuthorID");
      query.append(" WHERE ");
      query.append(DatabaseTables.EVENTS_HAS_AUTHORS);
      query.append(".EventID = ?");
      preparedStatement = connection.prepareStatement(query.toString());
      preparedStatement.setLong(1, eventId);
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        author = new String();
        author = result.getString("AuthorName");
        authors.add(author);
      }
    } catch (SQLException e) {
      LOGGER.error("Could not retrieve authors from the database", e);
    }

    return authors;
  }

}
