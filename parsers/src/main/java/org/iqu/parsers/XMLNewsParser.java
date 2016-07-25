package org.iqu.parsers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.iqu.parsers.entities.NewsArticle;
import org.iqu.parsers.entities.Source;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Parser implementation that reads RSS news feeds using DOM API.
 * 
 * @author Cristi Badoi
 *
 */
public class XMLNewsParser implements Parser<NewsArticle> {

  private static final Logger LOGGER = Logger.getLogger(XMLNewsParser.class);

  private Source source;

  @Override
  public Source getSource() {
    return source;
  }

  @Override
  public List<NewsArticle> readFeed(String sourceURL, String encoding) {
    List<NewsArticle> result = new ArrayList<NewsArticle>();
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document document = builder
          .parse(new InputSource(new InputStreamReader(new URL(sourceURL).openStream(), encoding)));
      document.getDocumentElement().normalize();

      readSourceInfo(document);
      readItems(sourceURL, result, document);

    } catch (ParserConfigurationException e) {
      LOGGER.error("DocumentBuilderFactory error!", e);
    } catch (MalformedURLException e) {
      LOGGER.error("Invalid URL!", e);
    } catch (SAXException e) {
      LOGGER.error("DocumentBuilder parsing error!", e);
    } catch (IOException e) {
      LOGGER.error("InputStream error!", e);
    }
    return result;
  }

  private void readItems(String sourceURL, List<NewsArticle> result, Document document) {
    NodeList nList = document.getDocumentElement().getElementsByTagName(XMLParserConstants.ITEM);

    for (int i = 0; i < nList.getLength(); i++) {
      Node node = nList.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        NewsArticle article = new NewsArticle();
        article.setTitle(getValue(element, XMLParserConstants.TITLE));
        article.setExternal_url(getValue(element, XMLParserConstants.EXTERNAL_URL));
        article.setId(getValue(element, XMLParserConstants.ID));
        article.setDescription(getValue(element, XMLParserConstants.DESCRIPTION));
        article.setCategories(getValues(element, XMLParserConstants.CATEGORY));
        article.setDate(convertDate(getValue(element, XMLParserConstants.DATE)));
        article.setImages(getAttributeValues(element, XMLParserConstants.IMAGE_MEDIA_CONTENT, XMLParserConstants.URL));
        article.setEnclosure(getAttributeValue(element, XMLParserConstants.IMAGE_ENCLOSURE, XMLParserConstants.URL));
        article.setAuthors(getValues(element, XMLParserConstants.DC_AUTHOR));
        article.setSource(sourceURL);
        result.add(article);
      }
    }
  }

  private void readSourceInfo(Document document) {
    source = new Source();
    Node sourceTitle = document.getDocumentElement().getElementsByTagName(XMLParserConstants.TITLE).item(0);
    source.setDisplayName(sourceTitle.getTextContent());
    Node sourceDescription = document.getDocumentElement().getElementsByTagName(XMLParserConstants.DESCRIPTION).item(0);
    source.setDescription(sourceDescription.getTextContent());
    Element imageNode = (Element) document.getDocumentElement().getElementsByTagName(XMLParserConstants.SOURCE_IMAGE)
        .item(0);
    Node imageUrl = imageNode.getElementsByTagName(XMLParserConstants.URL).item(0);
    source.setImage(imageUrl.getTextContent());
  }

  private String getValue(Element element, String tagName) {
    String result = "";
    if (element.getElementsByTagName(tagName).item(0) != null) {
      result = element.getElementsByTagName(tagName).item(0).getTextContent();
    }
    return result;
  }

  private String getAttributeValue(Element element, String tagName, String attributeName) {
    String result = "";
    if (element.getElementsByTagName(tagName).item(0) != null) {
      if (element.getElementsByTagName(tagName).item(0).getAttributes().getNamedItem(attributeName) != null) {
        result = element.getElementsByTagName(tagName).item(0).getAttributes().getNamedItem(attributeName)
            .getNodeValue();
      }
    }
    return result;
  }

  private List<String> getValues(Element element, String tagName) {
    List<String> result = new ArrayList<String>();
    NodeList nodeList = element.getElementsByTagName(tagName);
    for (int i = 0; i < nodeList.getLength(); i++) {
      result.add(nodeList.item(i).getTextContent());
    }
    return result;
  }

  private List<String> getAttributeValues(Element element, String tagName, String attributeName) {
    List<String> result = new ArrayList<String>();
    NodeList nodeList = element.getElementsByTagName(tagName);
    for (int i = 0; i < nodeList.getLength(); i++) {
      if (nodeList.item(i).getAttributes().getNamedItem(attributeName) != null) {
        result.add(nodeList.item(i).getAttributes().getNamedItem(attributeName).getNodeValue());
      }
    }
    return result;
  }

  private long convertDate(String date) {
    DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyy HH:mm:ss zzz");
    Date temp = null;
    try {
      temp = formatter.parse(date);
    } catch (ParseException e) {
      LOGGER.error("DateFormat parsing error!", e);
      return 0;
    }
    return temp.getTime() / 1000;
  }

}