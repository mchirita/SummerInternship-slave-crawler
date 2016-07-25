package org.iqu.persistence.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.iqu.persistence.entities.Author;
import org.iqu.persistence.entities.Category;
import org.iqu.persistence.entities.News;
import org.iqu.persistence.entities.Source;

public class NewsDAOImpl implements NewsDAO {

	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;
	private String query;
	private List<Integer> ids = new ArrayList<Integer>();
	ResultSet generatedKeys = null;

	public NewsDAOImpl() {

	}

	private void connectToDatabase() throws SQLException {

		if (connection == null) {
			connection = DAOFactoryImp.createConnection();
			statement = connection.createStatement();
		}
	}

	@Override
	public void create(News entity) {
		try {
			connectToDatabase();
			addSource(entity);
			addNews(entity);

			for (Author author : entity.getAuthors()) {
				addAuthor(author);
			}
			addNewsAndAuthorRelations(entity);

			for (Category category : entity.getCategories()) {
				addCategory(category);
			}
			addNewsAndCategoryRelations(entity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addNewsAndCategoryRelations(News entity) {
		query = "insert into News_has_Category(NewsID, CategoryID) values(?,?)";
		try {
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, entity.getId());
			for (Integer id : ids) {
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			ids.clear();
		}
		ids.clear();

	}

	private void addCategory(Category category) {
		query = "insert into Category(CategoryName) values(?)";
		try {
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, category.getName());
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				ids.add(generatedKeys.getInt(1));
			}
		} catch (SQLException e) {
			query = "select CategoryID from Category where CategoryName = ?";
			try {
				preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, category.getName());
				ResultSet result = preparedStatement.executeQuery();
				if (result.next()) {
					ids.add(result.getInt(1));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void addNewsAndAuthorRelations(News entity) {
		query = "insert into News_has_Authors(NewsID, AuthorID) values(?,?)";
		try {
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, entity.getId());
			for (Integer id : ids) {
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			ids.clear();
		}
		ids.clear();
	}

	private void addAuthor(Author author) {
		query = "insert into Authors(AuthorName) values(?)";
		try {
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, author.getName());
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				ids.add(generatedKeys.getInt(1));
			}
		} catch (SQLException e) {
			query = "select AuthorID from Authors where AuthorName = ?";
			try {
				preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, author.getName());
				ResultSet result = preparedStatement.executeQuery();
				if (result.next()) {
					ids.add(result.getInt(1));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void addNews(News entity) {
		query = "insert into News(Date, Title, Subtitle, Description, Body, SourceID) values(?,?,?,?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, entity.getDate());
			preparedStatement.setString(2, entity.getTitle());
			preparedStatement.setString(3, entity.getSubtitle());
			preparedStatement.setString(4, entity.getDescription());
			preparedStatement.setString(5, entity.getBody());
			preparedStatement.setInt(6, entity.getSource().getId());
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				entity.setId(generatedKeys.getInt(1));
			}
		} catch (SQLException e) {
			query = "select NewsID from News where Date = ?";
			try {
				preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, entity.getDate());
				ResultSet result = preparedStatement.executeQuery();
				if (result.next()) {
					entity.setId(result.getInt(1));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void addSource(News entity) {
		query = "insert into Sources(DisplayName, Description) values(?,?)";
		try {
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, entity.getSource().getDisplayName());
			preparedStatement.setString(2, entity.getSource().getDescription());
			preparedStatement.executeUpdate();
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				entity.getSource().setId(generatedKeys.getInt(1));
			}
		} catch (SQLException e) {
			query = "select SourceID from Sources where DisplayName = ? and Description = ?";
			try {
				preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, entity.getSource().getDisplayName());
				preparedStatement.setString(2, entity.getSource().getDescription());
				ResultSet result = preparedStatement.executeQuery();
				if (result.next()) {
					entity.getSource().setId(result.getInt(1));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void update(News entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public News find(News entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<News> findAll() {
		List<News> listOfNews = new ArrayList<News>();
		News news = null;
		try {
			connectToDatabase();
			query = "Select * from News";
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				news = new News();
				news.setId(result.getInt(1));
				news.setDate(result.getInt(2));
				news.setTitle(result.getString(3));
				news.setSubtitle(result.getString(4));
				news.setDescription(result.getString(5));
				Source source = findById(result);
				news.setSource(source);
				news.setBody(result.getString(6));
				listOfNews.add(news);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfNews;
	}

	private Source findById(ResultSet result) throws SQLException {
		String sourceQuery = "Select * from Sources where SourceId=?";
		preparedStatement = connection.prepareStatement(sourceQuery, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, result.getInt(6));
		ResultSet sourceResult = preparedStatement.executeQuery();
		Source source = new Source();
		if (sourceResult.next()) {
			source.setId(sourceResult.getInt(1));
			source.setDisplayName(sourceResult.getString(2));
			source.setDescription(sourceResult.getString(3));
		}
		return source;
	}

	@Override
	public void delete(News entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Author> retrieveAuthors() {
		List<Author> authors = new ArrayList<Author>();
		Author author = null;
		try {
			connectToDatabase();
			query = "Select * from Authors";
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				author = new Author();
				author.setAuthorID(result.getInt(1));
				author.setName(result.getString(2));
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
			query = "Select * from Sources";
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
	public List<Category> retrieveCategories() {
		List<Category> categories = new ArrayList<Category>();
		Category category = null;
		try {
			connectToDatabase();
			query = "Select * from Category";
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				category = new Category();
				category.setId(result.getInt(1));
				category.setName(result.getString(2));
				categories.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public List<News> findAllBySource(Source source) {
		try {
			connectToDatabase();
			String query = "Select * from News where SourceID = ?";
			News news = new News();
			List<News> result = new ArrayList<News>();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, source.getId());
			ResultSet rs = preparedStatement.executeQuery(query);
			while (rs.next()) {
				news.setId(rs.getString("NewsID"));
				news.setDate(rs.getInt("Date"));
				news.setTitle(rs.getString("Title"));
				news.setSubtitle(rs.getString("Subtitle"));
				news.setDescription(rs.getString("Description"));
				news.setSource(source);
				news.setBody(rs.getString("Body"));
				result.add(news);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

}
