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
import org.iqu.persistence.entities.Source;
import org.iqu.persistence.entities.Type;

public class EventDAOImpl implements EventDAO {

  private Connection connection;
  private PreparedStatement preparedStatement;
  private StringBuilder query = new StringBuilder();
  private List<Integer> ids = new ArrayList<Integer>();
  private ResultSet generatedKeys = null;
  private Source source = null;
  private static final Logger LOGGER = Logger.getLogger("EventDAOImpl.class");

  @Override
  public void create(EventDTO entity) {
    try {
      connectToDatabase();
      addTypes(entity);
      addEvent(entity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void addTypes(EventDTO entity) throws SQLException {
    query.setLength(0);
    query.append("INSERT into ");
    query.append(DatabaseTables.TYPES);
    query.append("(TypeName) ");
    query.append("values(?)");
    preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
    preparedStatement.setString(1, entity.getType());
    preparedStatement.executeUpdate();
    generatedKeys = preparedStatement.getGeneratedKeys();
    if (generatedKeys.next()) {

    }
  }

  @Override
  public void update(EventDTO entity) {
    // TODO Auto-generated method stub

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
  public List<Source> retrieveSources() {
    List<Source> sources = new ArrayList<Source>();
    Source source = null;
    try {
      connectToDatabase();
      query.setLength(0);
      query.append("SELECT * FROM ");
      query.append(DatabaseTables.SOURCES);
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      ResultSet result = preparedStatement.executeQuery();
      while (result.next()) {
        source = new Source();
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
  public List<EventDTO> findAllBySource(Source source) {
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

  private void addEvent(EventDTO entity) {
    query.setLength(0);
    query.append("INSERT into ");
    query.append(DatabaseTables.EVENTS);
    query.append("(StartDate, EndDate, Title, Subtitle, Description, SourceID, TypeID, Thumbnail_id, ExternalURL) ");
    query.append("values(?,?,?,?,?,?,?,?,?)");
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setLong(1, entity.getStartDate());
      preparedStatement.setLong(2, entity.getEndDate());
      preparedStatement.setString(3, entity.getTitle());
      preparedStatement.setString(4, entity.getSubtitle());
      preparedStatement.setString(5, entity.getDescription());
      preparedStatement.setInt(6, source.getId());
      preparedStatement.setInt(6, source.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void addSource(Source source) {
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
    // query = "insert into Events_has_Images(EventID, ImageID) values(?,?)";
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setLong(1, entity.getId());
      for (Integer id : ids) {
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      ids.clear();
    }
    ids.clear();

  }

  private void addImage(String image) {
    // query = "insert into Images(URL) values(?)";
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, image);
      preparedStatement.executeUpdate();
      generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        ids.add(generatedKeys.getInt(1));
      }
    } catch (SQLException e) {
      // query = "select ImageID from Images where URL = ?";
      try {
        preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
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
    // query = "insert into Events_has_Authors(EventID, AuthorID) values(?,?)";
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setLong(1, entity.getId());
      for (Integer id : ids) {
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException e) {
      ids.clear();
    }
    ids.clear();
  }

  private void addAuthor(String author) {
    // query = "insert into Authors(AuthorName) values(?)";
    try {
      preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, author);
      preparedStatement.executeUpdate();
      generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        ids.add(generatedKeys.getInt(1));
      }
    } catch (SQLException e) {
      // query = "select AuthorID from Authors where AuthorName = ?";
      try {
        preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
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
