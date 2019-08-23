package com.tochka.rss.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.springframework.stereotype.Component;

@Component
public class XML_Factory {
	
	public XMLEventReader getXmlReader(String strUrl) throws IOException, XMLStreamException {
		XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
		URL url = new URL(strUrl);
		InputStream inStream = url.openStream();

		return xmlFactory.createXMLEventReader(inStream);
	}

}
