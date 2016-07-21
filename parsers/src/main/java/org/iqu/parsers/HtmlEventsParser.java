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
	private static final Logger logger = Logger.getLogger(HtmlEventsParser.class);
	private static String pattern = "yyyy/MM/dd";
	private static DateFormat dF = new SimpleDateFormat(pattern);
	private long endDate = 0;
	private String categories = "";
	private String source;
	private String dateEnd;

	@Override
	public List<Event> readFeed(String sourceURL, String encoding) {

		List<Event> events = new ArrayList<Event>();
		source = sourceURL;
		Document doc = null;
		try {
			doc = Jsoup.connect(sourceURL).get();
		} catch (IOException e) {
			logger.error("Error loading URL");
		}
		Elements item = doc.getElementsByClass(EVENTGROUP);

		for (Element element : item) {

			String UrlToConnect = element.getElementsByTag(A).attr(URL);
			if (UrlToConnect != null && UrlToConnect.length() != 0)
				takeEvents(element.getElementsByTag(A).attr(URL), events);
		}

		return events;
	}

	private void takeEvents(String eventsURL, List<Event> events) {
		Document doc = null;
		try {
			doc = Jsoup.connect(eventsURL).get();
		} catch (MalformedURLException e) {
			logger.error("Invalid URL!", e);
		} catch (IOException e) {
			logger.error("InputStream error!", e);
		}
		categories = doc.body().id();
		Elements item = doc.getElementsByClass(EVENTLIST);
		for (Element element : item) {
			event = new Event();
			try {
				getTags(element);
			} catch (ParseException e) {
				logger.error("Invalid tags");
			}
			events.add(event);
		}

	}

	private void getTags(Element element) throws ParseException {
		event.setTitle(element.getElementsByTag(A).attr(TITLE));
		event.setImage_id(element.getElementsByTag(IMG).attr(IMGSRC));
		event.setExternal_url(element.getElementsByTag("a").attr(URL));
		event.setDescription(element.getElementsByClass(DESCRIPTION).html());
		event.setId(element.getElementsByClass(EVENTLIST).attr(ID) + "-" + categories);
		event.setStartDate(convertDate(element.getElementsByTag(TIME).attr(DATE)));
		event.setSource(source);
		event.setCategories(categories);
		dateEnd = element.getElementsByAttributeValue("itemprop", "endDate").attr(HtmlParserConstants.DATE);
		if (dateEnd.length() > 0) {
			try {
				endDate = convertDate(dateEnd);
			} catch (ParseException e) {
				logger.error("Invalid date format");
			}
		} else
			endDate = 0;
		event.setEndDate(endDate);
	}

	private long convertDate(String date) throws ParseException {
		date = date.substring(0, 10);
		date = date.replaceAll("-", "/");
		return dF.parse(date).getTime();
	}
}