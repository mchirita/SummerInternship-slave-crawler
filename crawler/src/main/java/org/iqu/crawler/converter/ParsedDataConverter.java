package org.iqu.crawler.converter;

import java.util.List;

import org.iqu.crawler.entities.ParsedDataModel;
import org.iqu.parsers.entities.EventModel;
import org.iqu.parsers.entities.NewsArticleModel;
import org.iqu.parsers.entities.SourceModel;

public class ParsedDataConverter {

  public ParsedDataDTO convertParsedData(ParsedDataModel model) {
    ParsedDataDTO result = new ParsedDataDTO();
    result.setNews(convertNews(model.getNews()));
    result.setEvents(convertEvents(model.getEvents()));
    result.setSource(convertSource(model.getSource()));
    return result;
  }

  public List<NewsArticleDTO> convertNews(List<NewsArticleModel> model) {
    List<NewsArticleDTO> result = new List<NewsArticleDTO>();
    for (NewsArticleModel article : model) {
      result.add(convertNewsArticle(article));
    }
  }

  public List<EventDTO> convertEvents(List<EventModel> model) {
    List<EventDTO> result = new List<EventDTO>();
    for (EventModel event : model) {
      result.add(convertEvent(event));
    }
  }

  public NewsArticleDTO convertNewsArticle(NewsArticleModel model) {
    NewsArticleDTO result = new NewsArticleDTO();
    result.setDate(model.getDate());
    result.setId(model.getId());
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
    result.setId(model.getId());
    result.setDescription(model.getDescription());
    result.setAuthors(model.getAuthors());
    result.setCategories(model.getCategories());
    result.setSource(model.getSource());
    result.setBody(model.getBody());
    result.setImage_id(model.getImage_id());
    result.setThumbnail_id(model.getThumbnail_id());
    result.setExternal_url(model.getExternal_url());
    return result;
  }

  public SourceDTO convertSource(SourceModel model) {
    SourceDTO result = new SourceDTO();
    result.setId(model.getId());
    result.setDisplayName(model.getDisplayName());
    result.setDescription(model.getDescription());
    result.setImage(model.getImage());
    return result;
  }

}
