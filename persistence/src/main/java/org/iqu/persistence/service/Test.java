package org.iqu.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.iqu.persistence.entities.EventDTO;
import org.iqu.persistence.entities.SourceDTO;

public class Test {

  public static void main(String[] args) {

    SourceDTO source = new SourceDTO();
    source.setDescription("source description");
    source.setDisplayName("some name");

    List<String> authors = new ArrayList<String>();
    authors.add("poc poc");
    authors.add("fasdfsdfother name");

    // List<String> categories = new ArrayList<String>();
    // categories.add("category name");
    // categories.add("dsdgsdgdgname");

    List<String> subtypes = new ArrayList<String>();
    subtypes.add("rock");
    subtypes.add("classic");

    List<String> images = new ArrayList<String>();
    images.add("agks");
    images.add("whtewjr");

    /*
     * NewsArticleDTO newsArticle = new NewsArticleDTO();
     * newsArticle.setGuid("33"); newsArticle.setDate(15462);
     * newsArticle.setTitle("a titel"); newsArticle.setSubtitle("s usubtitel");
     * newsArticle.setDescription("bla bla");
     * newsArticle.setSource(source.getDisplayName());
     * newsArticle.setAuthors(authors); newsArticle.setCategories(categories);
     * newsArticle.setBody("gogosi"); newsArticle.setImages(images);
     * newsArticle.setThumbnail_id("234");
     * newsArticle.setExternal_url("sdgasdf");
     */

    EventDTO event = new EventDTO();
    event.setStartDate(1235);
    event.setEndDate(1289);
    event.setTitle("Titlu Titlu");
    event.setSubtitle("subttiel");
    event.setDescription("poc poc");
    event.setSource(source.getDisplayName());
    event.setType("concert");
    event.setSubtypes(subtypes);
    event.setAuthors(authors);
    event.setImages(images);
    event.setBody("asfsdgds");
    event.setThumbnail_id("32");
    event.setExternal_url("aasfagagsdgsd");

    // NewsDAO news = DAOFactory.getNewsDAO();
    // news.addSource(source);
    // news.delete(newsArticle);
    // news.create(newsArticle);
    // news.update(newsArticle);
    // System.out.println(news.findAll());

    EventDAO eventAcc = DAOFactory.getEventDAO();
    // eventAcc.addSource(source);
    // eventAcc.create(event);
    // eventAcc.update(event);
    // eventAcc.delete(event);
    // System.out.println(eventAcc.retrieveTypesAndSubtypes());
    System.out.println(eventAcc.findAll());

  }

}
