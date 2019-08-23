package com.tochka.rss.conversion;

import javax.persistence.Embeddable;

@Embeddable
public class FieldConversion {
	private String fieldsName;
	private String xmlNodeName;
	public String getFieldsName() {
		return fieldsName;
	}
	public String getXmlNodeName() {
		return xmlNodeName;
	}
	public void setFieldsName(String fieldsName) {
		this.fieldsName = fieldsName;
	}
	public void setXmlNodeName(String xmlNodeName) {
		this.xmlNodeName = xmlNodeName;
	}
}

