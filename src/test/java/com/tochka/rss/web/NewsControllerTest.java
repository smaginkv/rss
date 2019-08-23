package com.tochka.rss.web;


import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.tochka.rss.db.NewsService;
import com.tochka.rss.service.ProcessingURL;

public class NewsControllerTest {

	@Autowired
	private ProcessingURL procService;
	
	@Test
	public void testHomePage() throws Exception {
		NewsService mockNewsService = mock(NewsService.class);
		
		NewsController controller = new NewsController(mockNewsService, procService);
		    MockMvc mockMvc = standaloneSetup(controller).build();
		    mockMvc.perform(get("/")).andExpect(view().name("news"));
	}
}
