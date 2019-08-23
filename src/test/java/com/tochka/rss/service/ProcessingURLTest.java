package com.tochka.rss.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.tochka.rss.db.NewsURL_Service;
import com.tochka.rss.domain.NewsURL;
import com.tochka.rss.parsing.SimpleXML_ParseRule;
import com.tochka.rss.parsing.Parsable;

public class ProcessingURLTest {
	private static final String TEST_CLASS_NAME = "test class name";
	private static NewsURL newsUrl;
	
	@Autowired
	private ApplicationContext context;
	
	@BeforeClass
	public static void initializeNewsURL() {
		newsUrl = new NewsURL();
		newsUrl.setUrl("test rss url");
		newsUrl.setClassName(TEST_CLASS_NAME);
	}
	
	@Test
	public void processingURLShouldFindAllOnNewsUrlRepo() {
		NewsURL_Service mockNewsUrlRepo = mock(NewsURL_Service.class);
		ProcessingURL processingURL = new ProcessingURL(context, mockNewsUrlRepo);
		processingURL.updateNews();
		verify(mockNewsUrlRepo, times(1)).findAll();
	}
	
	@Test
	public void processingURLShouldgetNewsByURLOnParseRule() throws Exception {
		
		NewsURL_Service mockNewsUrlRepo = prepareNewsUrlRepo();
		
		Parsable parseRule = mock(SimpleXML_ParseRule.class);		
		ApplicationContext appContext = prepareAppContext(parseRule);
		
		ProcessingURL processingURL = new ProcessingURL(appContext, mockNewsUrlRepo);
		processingURL.updateNews();		
		
		verify(parseRule, times(1)).getNewsByURL(newsUrl);
	}
	
	private NewsURL_Service prepareNewsUrlRepo() {		
		List<NewsURL> listNews = new ArrayList<>();
		listNews.add(newsUrl);
		
		NewsURL_Service mockNewsUrlRepo = mock(NewsURL_Service.class);
		when(mockNewsUrlRepo.findAll()).thenReturn(listNews);
		return mockNewsUrlRepo;
	}
	
	private ApplicationContext prepareAppContext(Parsable parseRule) {
		ApplicationContext appContext = mock(ApplicationContext.class);
		when(appContext.getBean(TEST_CLASS_NAME)).thenReturn(parseRule);
		return appContext;
	}
}
