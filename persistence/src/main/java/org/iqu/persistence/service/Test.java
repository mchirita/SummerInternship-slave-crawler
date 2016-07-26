package org.iqu.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.iqu.persistence.entities.NewsArticle;
import org.iqu.persistence.entities.Source;

public class Test {

	public static void main(String[] args) {
		DAOFactory factory = new DAOFactoryImp();

		Source source = new Source();
		source.setDescription("source description");
		source.setDisplayName("some name");

		List<String> authors = new ArrayList<String>();
		authors.add("asdas");
		authors.add("fasdfsdfother name");

		List<String> categories = new ArrayList<String>();
		categories.add("category name");
		categories.add("dsdgsdgdgname");

		List<String> images = new ArrayList<String>();
		images.add("agksldgsgsdhgsdh");
		images.add("whtewjr");

		NewsArticle newsArticle = new NewsArticle();
		newsArticle.setGuid("33");
		newsArticle.setDate(15462);
		newsArticle.setTitle("a titel");
		newsArticle.setSubtitle("s usubtitel");
		newsArticle.setDescription("bla bla");
		newsArticle.setSource(source.getDisplayName());
		newsArticle.setAuthors(authors);
		newsArticle.setCategories(categories);
		newsArticle.setBody("gogosi");
		newsArticle.setImages(images);
		newsArticle.setThumbnail_id("234");
		newsArticle.setExternal_url("sdgasdf");

		NewsDAO news = factory.getNewsDAO();
		news.addSource(source);
		// news.delete(newsArticle);
		news.create(newsArticle);
		// news.update(newsArticle);

	}

}
