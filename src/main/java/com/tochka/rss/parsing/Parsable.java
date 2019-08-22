package com.tochka.rss.parsing;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import com.tochka.rss.exception.InstanceFillingException;

public interface Parsable {	
	void getNewsByURL(String strUrl) throws XMLStreamException, InstanceFillingException, IOException;
}
