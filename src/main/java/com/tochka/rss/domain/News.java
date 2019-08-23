package com.tochka.rss.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 1000)
	private String title;

	@Column
	private String link;
	
	@ManyToOne(optional = false)
	private NewsURL newsURL;

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public NewsURL getNewsURL() {
		return newsURL;
	}

	public void setNewsURL(NewsURL newsURL) {
		this.newsURL = newsURL;
	}
}
