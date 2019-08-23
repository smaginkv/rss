package com.tochka.rss.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.tochka.rss.conversion.FieldsConversionRule;

@Entity
public class NewsURL {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String url;

	@Column
	private String className;
	
	@ManyToOne(optional = false)
	private FieldsConversionRule conversion;

	public NewsURL() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public FieldsConversionRule getConversion() {
		return conversion;
	}

	public void setConversion(FieldsConversionRule conversion) {
		this.conversion = conversion;
	}
}
