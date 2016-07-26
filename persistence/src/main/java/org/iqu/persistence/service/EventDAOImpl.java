package org.iqu.persistence.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.iqu.persistence.entities.Event;
import org.iqu.persistence.entities.Source;
import org.iqu.persistence.entities.Type;

public class EventDAOImpl implements EventDAO {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private StringBuilder query = new StringBuilder();
	private List<Integer> ids = new ArrayList<Integer>();
	private ResultSet generatedKeys = null;
	private Source source = null;

	@Override
	public void create(Event entity) {

	}

	@Override
	public void update(Event entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Event find(Event entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Event entity) {
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
			query.append(Tables.AUTHORS);
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
			query.append(Tables.SOURCES);
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
	public List<Event> findAllBySource(Source source) {
		Event event = null;
		List<Event> result = new ArrayList<Event>();

		try {
			connectToDatabase();
			query.setLength(0);
			query.append("SELECT SourceID FROM ");
			query.append(Tables.SOURCES);
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
			query.append(Tables.EVENTS);
			query.append(" where SourceID = ?");
			preparedStatement = connection.prepareStatement(query.toString());
			preparedStatement.setInt(1, source.getId());
			ResultSet rs = preparedStatement.executeQuery(query.toString());
			while (rs.next()) {
				event = new Event();
				event.setId(rs.getString("EventID"));
				event.setDate(rs.getLong("Date"));
				event.setTitle(rs.getString("Title"));
				event.setSubtitle(rs.getString("Subtitle"));
				event.setDescription(rs.getString("Description"));
				event.setType("TypeID");
				event.setSource(source.getDisplayName());
				result.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	private void addEvent(Event entity) {
		// query = "insert into Events(EventID, Date, Title, Subtitle, Description,
		// TypeID, SourceID) values(?,?,?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, entity.getId());
			preparedStatement.setLong(2, entity.getDate());
			preparedStatement.setString(3, entity.getTitle());
			preparedStatement.setString(4, entity.getSubtitle());
			preparedStatement.setString(5, entity.getDescription());
			preparedStatement.setString(6, entity.getBody());
			preparedStatement.setInt(7, source.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addSource(Source source) {
		this.source = source;
		// query = "insert into Sources(DisplayName, Description) values(?,?)";
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
			// query = "select SourceID from Sources where DisplayName = ? and
			// Description = ?";
			try {
				preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, source.getDisplayName());
				preparedStatement.setString(2, source.getDescription());
				ResultSet result = preparedStatement.executeQuery();
				if (result.next()) {
					source.setId(result.getInt(1));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	private void connectToDatabase() throws SQLException {

		if (connection == null) {
			connection = DAOFactoryImp.createConnection();
		}
	}

	private void addEventAndImageRelations(Event entity) {
		// query = "insert into Events_has_Images(EventID, ImageID) values(?,?)";
		try {
			preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, entity.getId());
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

	private void addEventAndAuthorRelations(Event entity) {
		// query = "insert into Events_has_Authors(EventID, AuthorID) values(?,?)";
		try {
			preparedStatement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, entity.getId());
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
