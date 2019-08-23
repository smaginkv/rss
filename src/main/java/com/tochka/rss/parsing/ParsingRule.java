package com.tochka.rss.parsing;

import java.io.IOException;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import com.tochka.rss.conversion.NewsField;
import com.tochka.rss.db.NewsService;
import com.tochka.rss.domain.NewsURL;
import com.tochka.rss.exception.InstanceFillingException;
import com.tochka.rss.service.XML_Factory;

public abstract class ParsingRule {
	protected NewsService newsRepo;
	protected XML_Factory xmlFactory;
	protected Map<String, NewsField> fieldsMap;
	protected String itemTag;
	
	public abstract void getNewsByURL(NewsURL strUrl) throws XMLStreamException, InstanceFillingException, IOException;
}
