package org.iqu.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.iqu.persistence.entities.Author;
import org.iqu.persistence.entities.Category;
import org.iqu.persistence.entities.News;
import org.iqu.persistence.entities.Source;

public class Test {

	public static void main(String[] args) {
		DAOFactory factory = new DAOFactoryImp();

		Source source = new Source();
		source.setDescription("source description");
		source.setDisplayName("some name");

		List<Author> authors = new ArrayList<Author>();
		Author author = new Author();
		Author author2 = new Author();
		author.setName("a name");
		author2.setName("another name");
		authors.add(author);
		authors.add(author2);

		List<Category> categories = new ArrayList<Category>();
		Category category1 = new Category();
		Category category2 = new Category();
		category1.setName("category name");
		category2.setName("another category name");
		categories.add(category1);
		categories.add(category2);

		News newsArticle = new News();
		newsArticle.setDate(15474);
		newsArticle.setTitle("a titel");
		newsArticle.setSubtitle("s usubtitel");
		newsArticle.setDescription("bla bla");
		newsArticle.setSource(source);
		newsArticle.setAuthors(authors);
		newsArticle.setCategories(categories);
		newsArticle.setBody("gogosi");

		NewsDAO news = factory.getNewsDAO();
		news.create(newsArticle);

	}

}
