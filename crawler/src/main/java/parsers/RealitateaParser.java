package parsers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import entities.NewsArticle;

/**
 * NewsParser implementation customised for "www.realitatea.net".
 * 
 * @author Cristi Badoi
 *
 */
public class RealitateaParser implements NewsParser {

  @Override
  public List<NewsArticle> readFeed(String sourceURL, String encoding) {
    List<NewsArticle> result = new ArrayList<NewsArticle>();
    try {
      URL url = new URL(sourceURL);
      XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(url.openStream(), encoding);
      NewsArticle article = new NewsArticle();

      while (reader.hasNext()) {
        XMLEvent event = reader.nextEvent();

        switch (event.getEventType()) {
        case XMLStreamConstants.START_ELEMENT:
          String name = event.asStartElement().getName().getLocalPart();
          switch (name.toLowerCase()) {
          case "item":
            article = new NewsArticle();
            break;
          case "title":
            if (reader.peek().isCharacters()) {
              article.setTitle(reader.nextEvent().asCharacters().getData());
            }
            break;
          case "link":
            if (reader.peek().isCharacters()) {
              article.setExternal_url(reader.nextEvent().asCharacters().getData());
            }
            break;
          case "guid":
            if (reader.peek().isCharacters()) {
              article.setId(reader.nextEvent().asCharacters().getData());
            }
            break;
          case "description":
            if (reader.peek().isCharacters()) {
              article.setDescription(reader.nextEvent().asCharacters().getData());
            }
            break;
          case "category":
            if (reader.peek().isCharacters()) {
              article.getCategories().add(reader.nextEvent().asCharacters().getData());
            }
            break;
          case "pubdate":
            if (reader.peek().isCharacters()) {
              DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyy HH:mm:ss zzz");
              try {
                Date date = formatter.parse(reader.nextEvent().asCharacters().getData());
                article.setDate(date.getTime() / 1000);
              } catch (ParseException e) {
                e.printStackTrace();
              }
            }
          }
          break;
        case XMLStreamConstants.END_ELEMENT:
          if (event.asEndElement().getName().getLocalPart().equals("item")) {
            article.setSource("www.realitatea.net");
            result.add(article);
          }
        }
      }
      reader.close();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (FactoryConfigurationError e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

}
