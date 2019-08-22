package com.tochka.rss.service;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.tochka.rss.db.NewsURL_Service;
import com.tochka.rss.domain.NewsURL;
import com.tochka.rss.exception.InstanceFillingException;
import com.tochka.rss.parsing.Parsable;

@Component
public class ProcessingURL {
	private ApplicationContext context;
	private NewsURL_Service newsUrlRepo;

	@Autowired
	public ProcessingURL(ApplicationContext context, NewsURL_Service newsUrlRepo) {
		this.context = context;
		this.newsUrlRepo = newsUrlRepo;
	}

	public void updateNews() {

		List<NewsURL> listUrl = newsUrlRepo.findAll();
		for (NewsURL url : listUrl) {
			try {
				performParsing(context, url);
			} catch (Exception e) {
				// TODO record to error log
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void performParsing(ApplicationContext context, NewsURL newsURL)
			throws BeansException, ClassNotFoundException, XMLStreamException, InstanceFillingException, IOException {
		String className = newsURL.getClassName();
		String url = newsURL.getUrl();

		if (className.isEmpty() || url.isEmpty()) {
			throw new IllegalArgumentException("url or rule parsing is not filled");
		}
		Parsable parseRule = (Parsable) context.getBean(Class.forName(className));
		parseRule.getNewsByURL(url);
	}
}
