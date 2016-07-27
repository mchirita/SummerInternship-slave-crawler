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
import org.iqu.persistence.entities.Type;

public class EventDAOImpl implements EventDAO {

  private Connection connection;
  private PreparedStatement preparedStatement;
  private StringBuilder query = new StringBuilder();
  private List<Integer> ids = new ArrayList<Integer>();
  private ResultSet generatedKeys = null;
  private SourceDTO source = null;
  private Type type = new Type();
  private static final Logger LOGGER = Logger.getLogger("EventDAOImpl.class");

  @Override
  public void create(EventDTO entity) {
    try {
      connectToDatabase();
      addTypes(entity);
      addEvent(entity);
      createRelations(entity);
    } catch (SQLException e) {
      e.printStackTrace();
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
        e1.printStackTrace();
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

  private void addTypeAndSubtypeRelations(Type type) {
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
      e.printStackTrace();
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
      LOGGER.info("Type already in database thus Retrieving the Type id", e);
      query.setLength(0);
      query.append("SELECT SourceID from ");
      query.append(DatabaseTables.SOURCES);
      query.append(" where DisplayName = ?, Description = ?");
      try {
        preparedStatement = connection.prepareStatement(query.toString());
        preparedStatement.setString(1, source.getDisplayName());
        preparedStatement.setString(2, source.getDescription());
        ResultSet result = preparedStatement.executeQuery();
        if (result.next()) {
          type.setId(generatedKeys.getLong(1));
        }
      } catch (SQLException e1) {
        LOGGER.error("Could not fetch the Type id", e1);
      }
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
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

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
    query.append(DatabaseTables.NEWS);
    query.append(" WHERE ExternalURL = ?");
    preparedStatement = connection.prepareStatement(query.toString());
    preparedStatement.setString(1, entity.getExternal_url());
    ResultSet res = preparedStatement.executeQuery();
    if (res.next()) {
      eventId = res.getLong(1);
    }

    return eventId;
  }

  @Override
  public EventDTO find(EventDTO entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<EventDTO> findAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void delete(EventDTO entity) {
    // TODO Auto-generated method stub

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
      e.printStackTrace();
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
      e.printStackTrace();
    }
    return sources;
  }

  @Override
  public List<Type> retrieveTypesAndSubtypes() {
    // TODO Auto-generated method stub
    return null;
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
      e.printStackTrace();
    }

    return result;
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
      query.append(" where DisplayName = ?, Description = ?");
      try {
        preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
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
      e.printStackTrace();
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
        e1.printStackTrace();
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
        e1.printStackTrace();
      }
    }
  }

}
