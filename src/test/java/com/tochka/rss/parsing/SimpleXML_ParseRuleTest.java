package com.tochka.rss.parsing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import com.tochka.rss.conversion.FieldsConversionRule;
import com.tochka.rss.conversion.NewsField;
import com.tochka.rss.db.NewsService;
import com.tochka.rss.domain.NewsURL;
import com.tochka.rss.service.XML_Factory;

public class SimpleXML_ParseRuleTest {

	private static final String TEST_XML_TEXT = "<rss version=\"2.0\"><channel><item><title>title1</title><link>link1</link><guid>1289042</guid></item><item><title>title2</title><link>link2</link><guid>1288892</guid></item></channel></rss>";
	private static final String TEST_RSS_URL = "test rss url";
	
	@Test
	public void processingURLShouldFindAllOnNewsUrlRepo() throws Exception {
		NewsService mockNewsRepo = mock(NewsService.class);
		XML_Factory xmlFactory = prepareXML_Factory();		
		
		NewsURL newsURL = new NewsURL();
		newsURL.setUrl(TEST_RSS_URL);
		newsURL.setConversion(getMockConversion());
		
		SimpleXML_ParseRule parseRule = new SimpleXML_ParseRule(mockNewsRepo, xmlFactory);
		parseRule.getNewsByURL(newsURL);
	}
	
	private XML_Factory prepareXML_Factory() throws XMLStreamException, IOException {
		XML_Factory mockXMLFactory = mock(XML_Factory.class);
		
		XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
		InputStream inStream = new ByteArrayInputStream(TEST_XML_TEXT.getBytes(StandardCharsets.UTF_8));
		XMLEventReader event = xmlFactory.createXMLEventReader(inStream);		
		
		when(mockXMLFactory.getXmlReader(TEST_RSS_URL)).thenReturn(event);
		
		return mockXMLFactory;
	}
	
	private FieldsConversionRule getMockConversion() {
		FieldsConversionRule mockConversion = mock(FieldsConversionRule.class);
		when(mockConversion.getItemTag()).thenReturn("item");
		
		HashMap<String, NewsField> map = new HashMap<>();
		map.put("title", new NewsField("Title"));
		map.put("link", new NewsField("Link"));		
		when(mockConversion.getFieldsMap()).thenReturn(map);
		return mockConversion;
	}
}
