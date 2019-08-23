package com.tochka.rss.parsing;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tochka.rss.conversion.NewsField;
import com.tochka.rss.db.NewsService;
import com.tochka.rss.domain.News;
import com.tochka.rss.domain.NewsURL;
import com.tochka.rss.exception.InstanceFillingException;
import com.tochka.rss.service.XML_Factory;

@Component(value = "simple string xml")
public class SimpleXML_ParseRule extends Parsable {

	@Autowired
	public SimpleXML_ParseRule(NewsService newsRepo, XML_Factory xmlFactory) {
		this.newsRepo = newsRepo;
		this.xmlFactory = xmlFactory;
	}

	@Override
	public void getNewsByURL(NewsURL newsUrl) throws InstanceFillingException, XMLStreamException, IOException {
		itemTag = newsUrl.getConversion().getItemTag();
		fieldsMap = newsUrl.getConversion().getFieldsMap();
		XMLEventReader xmlReader = xmlFactory.getXmlReader(newsUrl.getUrl());

		while (xmlReader.hasNext()) {
			XMLEvent event = xmlReader.nextEvent();

			String nodeName = getNodeName(event);
			if (nodeName.isEmpty()) {
				continue;
			}

			if (event.isStartElement() && nodeName.equalsIgnoreCase(itemTag)) {
				clearField();
			}

			else if (event.isEndElement() && nodeName.equalsIgnoreCase(itemTag)) {
				saveInstance(newsUrl);

			} else if (event.isStartElement()) {
				parseNode(nodeName, xmlReader);
			}
		}
	}

	private void saveInstance(NewsURL newsURL) throws InstanceFillingException {
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
			news.setNewsURL(newsURL);
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
}
