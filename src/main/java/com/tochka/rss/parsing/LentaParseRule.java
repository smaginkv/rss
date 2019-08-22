package com.tochka.rss.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tochka.rss.db.NewsService;
import com.tochka.rss.domain.News;
import com.tochka.rss.exception.InstanceFillingException;

@Component
public class LentaParseRule implements Parsable {
	private final String ITEM;
	private NewsService newsRepo;
	private Map<String, NewsField> fieldsMap;

	@Autowired
	public LentaParseRule(NewsService newsRepo) {
		this.newsRepo = newsRepo;
		
		// i want to have all settings to parsing in one place
		ITEM = "item";
		fieldsMap = getFieldsMap();
	}

	@Override
	public void getNewsByURL(String strUrl) throws InstanceFillingException, XMLStreamException, IOException {
		XMLEventReader xmlReader = getXmlReader(strUrl);

		while (xmlReader.hasNext()) {
			XMLEvent event = xmlReader.nextEvent();

			String nodeName = getNodeName(event);
			if (nodeName.isEmpty()) {
				continue;
			}

			if (event.isStartElement() && nodeName.equalsIgnoreCase(ITEM)) {
				clearField();
			}

			else if (event.isEndElement() && nodeName.equalsIgnoreCase(ITEM)) {
				saveInstance();

			} else if (event.isStartElement()) {
				parseNode(nodeName, xmlReader);
			}
		}
	}

	private void saveInstance() throws InstanceFillingException {
		News news = new News();

		for (NewsField newsField : fieldsMap.values()) {
			// setLink for example
			Method method;
			try {
				method = News.class.getMethod("set" + newsField.getFieldsName(), String.class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new InstanceFillingException(e);
			}

			// news.setLink("Lenta.ru")
			try {
				method.invoke(news, newsField.getValue());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new InstanceFillingException(e);
			}
		}

		newsRepo.saveOrUpdate(news);
		clearField();
	}

	private void clearField() {
		fieldsMap.forEach((key, value) -> value.setValue(""));
	}

	private void parseNode(String nodeName, XMLEventReader xmlReader) {
		NewsField newsField = fieldsMap.get(nodeName);

		if (newsField != null) {
			newsField.setValue(getData(xmlReader));
		}
	}

	private String getNodeName(XMLEvent event) {
		QName qualifiedName;
		if (event.isStartElement()) {
			qualifiedName = event.asStartElement().getName();

		} else if (event.isEndElement()) {
			qualifiedName = event.asEndElement().getName();

		} else {
			return "";
		}
		return qualifiedName.getLocalPart();
	}

	private String getData(XMLEventReader xmlReader) {
		XMLEvent event;
		String result = "";
		try {
			event = xmlReader.nextEvent();
		} catch (XMLStreamException e) {
			// TODO record to error log
			return "";
		}
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result;
	}

	private XMLEventReader getXmlReader(String strUrl) throws IOException, XMLStreamException {
		XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
		URL url = new URL(strUrl);
		InputStream inStream = url.openStream();

		return xmlFactory.createXMLEventReader(inStream);
	}

	private Map<String, NewsField> getFieldsMap() {
		HashMap<String, NewsField> map = new HashMap<>();

		map.put("link", new NewsField("Link"));
		map.put("title", new NewsField("Title"));
		return map;
	}

	private class NewsField {
		public final String fieldsName;
		public String value;

		public NewsField(String fieldsName) {
			this.fieldsName = fieldsName;
			this.value = "";
		}

		public String getFieldsName() {
			return fieldsName;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
