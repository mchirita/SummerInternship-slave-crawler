package org.iqu.parsers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;
import org.iqu.parsers.entities.NewsArticle;

/**
 * Parser implementation that reads news articles from RSS feeds using StAX API.
 * 
 * @author Cristi Badoi
 *
 */
public class StaxNewsParser implements Parser<NewsArticle> {
  private static final Logger LOGGER = Logger.getLogger(StaxNewsParser.class);

  private XMLEventReader reader;
  private NewsArticle article;
  private XMLEvent event;
  private List<NewsArticle> result;
  private String source;

  @Override
  public List<NewsArticle> readFeed(String sourceURL, String encoding) {
    source = sourceURL;
    result = new ArrayList<NewsArticle>();
    try {
      URL url = new URL(sourceURL);
      reader = XMLInputFactory.newInstance().createXMLEventReader(url.openStream(), encoding);
      skipToFirstItem(reader);

      while (reader.hasNext()) {
        event = reader.nextEvent();

        if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
          processStartElement();
        } else if (event.getEventType() == XMLStreamConstants.END_ELEMENT) {
          processEndElement();
        }
      }
      reader.close();
    } catch (XMLStreamException e) {
      LOGGER.error("XMLEventReader error!", e);
    } catch (FactoryConfigurationError e) {
      LOGGER.error("XMLInputFactory error!", e);
    } catch (IOException e) {
      LOGGER.error("InputStream error!", e);
    }
    return result;
  }

  private void processStartElement() {
    try {
      switch (event.asStartElement().getName().getLocalPart()) {
      case ParserConstants.ITEM:
        article = new NewsArticle();
        break;
      case ParserConstants.TITLE:
        article.setTitle(parseCharacters());
        break;
      case ParserConstants.EXTERNAL_URL:
        article.setExternal_url(parseCharacters());
        break;
      case ParserConstants.ID:
        article.setId(parseCharacters());
        break;
      case ParserConstants.DESCRIPTION:
        article.setDescription(parseCharacters());
        break;
      case ParserConstants.CATEGORY:
        article.getCategories().add(parseCharacters());
        break;
      case ParserConstants.DATE:
        DateFormat formatter = new SimpleDateFormat(ParserConstants.DATE_FORMAT);
        Date date = formatter.parse(parseCharacters());
        article.setDate(date.getTime() / 1000);
        break;
      case ParserConstants.IMAGE_MEDIA_CONTENT:
        String imageURL = event.asStartElement().getAttributeByName(new QName("url")).getValue();
        article.getImages().add(imageURL);
        break;
      case ParserConstants.AUTHOR:
        article.getAuthors().add(parseCharacters());
        break;
      case ParserConstants.IMAGE_ENCLOSURE:
        String enclosureURL = event.asStartElement().getAttributeByName(new QName("url")).getValue();
        article.setEnclosure(enclosureURL);
      }
    } catch (ParseException e) {
      LOGGER.error("DateFormat parsing error!", e);
    }
  }

  private void processEndElement() {
    if (event.asEndElement().getName().getLocalPart().equals("item")) {
      article.setSource(source);
      result.add(article);
    }
  }

  private String parseCharacters() {
    String text = "";
    try {
      while (reader.peek().isCharacters()) {
        text += reader.nextEvent().asCharacters().getData();
      }
    } catch (XMLStreamException e) {
      LOGGER.error("XMLStreamReader error!", e);
    }
    return text;
  }

  // skips everything until the first item to avoid tag name conflicts
  private void skipToFirstItem(XMLEventReader reader) {
    try {
      while (true) {
        if (reader.peek().isStartElement()) {
          if (reader.peek().asStartElement().getName().getLocalPart().equals("item")) {
            break;
          }
        }
        reader.nextEvent();
      }
    } catch (XMLStreamException e) {
      LOGGER.error("XMLStreamReader error!", e);
    }
  }

}
