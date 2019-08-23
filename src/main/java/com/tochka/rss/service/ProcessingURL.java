package com.tochka.rss.service;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.tochka.rss.db.NewsURL_Service;
import com.tochka.rss.domain.NewsURL;
import com.tochka.rss.exception.InstanceFillingException;
import com.tochka.rss.parsing.ParsingRule;

@Component
public class ProcessingURL {
	
	private NewsURL_Service newsUrlRepo;
	private ApplicationContext context;

	@Autowired
	public ProcessingURL(ApplicationContext context, NewsURL_Service newsUrlRepo) {
		this.newsUrlRepo = newsUrlRepo;
		this.context = context;
	}

	public void updateNews() {

		List<NewsURL> listUrl = newsUrlRepo.findAll();
		for (NewsURL url : listUrl) {
			try {
				performParsing(url);
			} catch (Exception e) {
				// TODO record to error log
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void performParsing(NewsURL newsURL) throws XMLStreamException, InstanceFillingException, IOException {
		
		String url = newsURL.getUrl(), className = newsURL.getClassName();
		
		if (className.isEmpty() || url.isEmpty()) {
			throw new IllegalArgumentException("url or rule parsing is not filled");
		}
		
		//value in annotation 'component'
		ParsingRule parseRule = (ParsingRule) context.getBean(className);
		
		parseRule.getNewsByURL(newsURL);
	}
}
