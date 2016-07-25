package org.iqu.parsers;

import static org.iqu.parsers.HtmlParserConstants.A;
import static org.iqu.parsers.HtmlParserConstants.DATE;
import static org.iqu.parsers.HtmlParserConstants.DESCRIPTION;
import static org.iqu.parsers.HtmlParserConstants.EVENTGROUP;
import static org.iqu.parsers.HtmlParserConstants.EVENTLIST;
import static org.iqu.parsers.HtmlParserConstants.ID;
import static org.iqu.parsers.HtmlParserConstants.IMG;
import static org.iqu.parsers.HtmlParserConstants.IMGSRC;
import static org.iqu.parsers.HtmlParserConstants.TIME;
import static org.iqu.parsers.HtmlParserConstants.TITLE;
import static org.iqu.parsers.HtmlParserConstants.URL;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.iqu.parsers.entities.Event;
import org.iqu.parsers.entities.Source;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class that implements a parser that makes an entity from html tags.
 * 
 * @author Razvan Rosu
 *
 */
public class HtmlEventsParser implements Parser<Event> {

  private Event event;
  private static final Logger LOGGER = Logger.getLogger(HtmlEventsParser.class);
  private static String pattern = "yyyy-MM-dd'T'HH:mm:ss'+'hh:mm";
  private static DateFormat dF = new SimpleDateFormat(pattern);
  private String categories = "";
  private String sourceURL;
  private Source source;

  @Override
  public Source getSource() {
    return source;
  }

  @Override
  public List<Event> readFeed(String sourceURL, String encoding) {

    List<Event> events = new ArrayList<Event>();
    this.sourceURL = sourceURL;
    Document doc = null;
    try {
      Connection connection = Jsoup.connect(sourceURL);
      if (connection != null) {
        doc = connection.get();
        readSourceInfo(doc);
        readItems(events, doc);
      }

    } catch (IOException e) {
      LOGGER.error("Error loading URL", e);
    }

    return events;
  }

  private void readSourceInfo(Document doc) {
    // TODO: Read information about the source.

  }

  private void readItems(List<Event> events, Document doc) {
    Elements item = doc.getElementsByClass(EVENTGROUP);

    for (Element element : item) {
      String UrlToConnect = element.getElementsByTag(A).attr(URL);
      if (UrlToConnect != null && UrlToConnect.length() != 0) {
        takeEvents(element.getElementsByTag(A).attr(URL), events);
      }
    }
  }

  private void takeEvents(String eventsURL, List<Event> events) {
    Document doc = null;
    try {
      Connection connection = Jsoup.connect(eventsURL);
      if (connection != null) {
        doc = connection.get();
      }
    } catch (MalformedURLException e) {
      LOGGER.error("Invalid URL!", e);
    } catch (IOException e) {
      LOGGER.error("InputStream error!", e);
    }
    categories = doc.body().id();
    Elements item = doc.getElementsByClass(EVENTLIST);
    for (Element element : item) {
      event = new Event();
      try {
        getTags(element);
        events.add(event);
      } catch (ParseException e) {
        LOGGER.error("Invalid tags", e);
      }
    }

  }

  private void getTags(Element element) throws ParseException {
    event.setTitle(element.getElementsByTag(A).attr(TITLE));
    event.setImage_id(element.getElementsByTag(IMG).attr(IMGSRC));
    event.setExternal_url(element.getElementsByTag("a").attr(URL));
    event.setDescription(element.getElementsByClass(DESCRIPTION).html());
    event.setId(element.getElementsByClass(EVENTLIST).attr(ID) + "-" + categories);
    event.setStartDate(convertDate(element.getElementsByTag(TIME).attr(DATE)));
    event.setSource(sourceURL);
    event.setCategories(categories);
    event.setEndDate(
        convertDate(element.getElementsByAttributeValue("itemprop", "endDate").attr(HtmlParserConstants.DATE)));
  }

  private long convertDate(String date) throws ParseException {
    if (date.length() > 0) {
      return dF.parse(date).getTime();
    }
    return 0;
  }

}