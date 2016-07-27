package org.iqu.crawler.converter;

import java.util.ArrayList;
import java.util.List;

import org.iqu.crawler.entities.ParsedDataModel;
import org.iqu.parsers.entities.EventModel;
import org.iqu.parsers.entities.NewsArticleModel;
import org.iqu.parsers.entities.SourceModel;
import org.iqu.persistence.entities.EventDTO;
import org.iqu.persistence.entities.NewsArticleDTO;
import org.iqu.persistence.entities.ParsedDataDTO;
import org.iqu.persistence.entities.SourceDTO;

public class ParsedDataConverter {

  public ParsedDataDTO convertParsedData(ParsedDataModel model) {
    ParsedDataDTO result = new ParsedDataDTO();
    result.setNews(convertNews(model.getNews()));
    result.setEvents(convertEvents(model.getEvents()));
    result.setSource(convertSource(model.getSource()));
    return result;
  }

  public List<NewsArticleDTO> convertNews(List<NewsArticleModel> model) {
    List<NewsArticleDTO> result = new ArrayList<NewsArticleDTO>();
    for (NewsArticleModel article : model) {
      result.add(convertNewsArticle(article));
    }
    return result;
  }

  public List<EventDTO> convertEvents(List<EventModel> model) {
    List<EventDTO> result = new ArrayList<EventDTO>();
    for (EventModel event : model) {
      result.add(convertEvent(event));
    }
    return result;
  }

  public NewsArticleDTO convertNewsArticle(NewsArticleModel model) {
    NewsArticleDTO result = new NewsArticleDTO();
    result.setDate(model.getDate());
    result.setGuid(model.getGuid());
    result.setTitle(model.getTitle());
    result.setSubtitle(model.getSubtitle());
    result.setDescription(model.getDescription());
    result.setAuthors(model.getAuthors());
    result.setCategories(model.getCategories());
    result.setSource(model.getSource());
    result.setBody(model.getBody());
    result.setImages(model.getImages());
    result.setThumbnail_id(model.getThumbnail_id());
    result.setExternal_url(model.getExternal_url());
    result.setEnclosure(model.getEnclosure());
    return result;
  }

  public EventDTO convertEvent(EventModel model) {
    EventDTO result = new EventDTO();
    result.setTitle(model.getTitle());
    result.setSubtitle(model.getSubtitle());
    result.setStartDate(model.getStartDate());
    result.setEndDate(model.getEndDate());
    result.setDescription(model.getDescription());
    result.setAuthors(model.getAuthors());
    result.setType(model.getType());
    result.setSubtypes(model.getSubtypes());
    result.setSource(model.getSource());
    result.setBody(model.getBody());
    result.setImages(model.getImages());
    result.setThumbnail_id(model.getThumbnail_id());
    result.setExternal_url(model.getExternal_url());
    return result;
  }

  public SourceDTO convertSource(SourceModel model) {
    SourceDTO result = new SourceDTO();
    result.setDisplayName(model.getDisplayName());
    result.setDescription(model.getDescription());
    result.setImage(model.getImage());
    return result;
  }

}
