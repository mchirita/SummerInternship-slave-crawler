package org.iqu.persistence.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.iqu.persistence.entities.Author;
import org.iqu.persistence.entities.Category;
import org.iqu.persistence.entities.News;
import org.iqu.persistence.entities.Source;

public class NewsService implements NewsDAO {

	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;

	public NewsService() {

	}

	private void connectToDatabase() throws SQLException {

		connection = DAOFactoryImp.createConnection();
		statement = connection.createStatement();

	}

	@Override
	public void create(News entity) {
		try {
			connectToDatabase();
			String sourceTableSeq = "insert into Sources(SourceId, DisplayName, Description) values(?,?,?)";
			preparedStatement = connection.prepareStatement(sourceTableSeq);
			preparedStatement.setString(1, entity.getSource().getDisplayName());
			preparedStatement.setString(2, entity.getSource().getDescription());
			preparedStatement.executeUpdate();

			String authorTableSeq = "insert into Author(AuthorName) values(?)";
			preparedStatement = connection.prepareStatement(authorTableSeq);
			for (Author author : entity.getAuthors()) {
				preparedStatement.setString(1, author.getName());
				preparedStatement.executeUpdate();
			}

			String categoryTableSeq = "insert into Category(CategoryName) values(?)";
			preparedStatement = connection.prepareStatement(categoryTableSeq);
			for (Category category : entity.getCategories()) {
				preparedStatement.setString(1, category.getName());
				preparedStatement.executeUpdate();
			}

			String newsTableSeq = "insert into News (Date, Title, Subtitle, Description, SourceID, Body) values(?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(newsTableSeq);
			preparedStatement.setInt(1, entity.getDate());
			preparedStatement.setString(1, entity.getTitle());
			preparedStatement.setString(1, entity.getSubtitle());
			preparedStatement.setString(1, entity.getDescription());
			preparedStatement.setString(1, entity.getSource().getId());
			preparedStatement.setString(1, entity.getBody());

			System.out.println(newsTableSeq);
			statement.executeUpdate(newsTableSeq);
		} catch (SQLException e) {
			e.printStackTrace();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(News entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Author> retrieveAuthors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Source> retrieveSources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> retrieveCategories() {
		// TODO Auto-generated method stub
		return null;
	}

}
