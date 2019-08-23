package com.tochka.rss.web;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import com.tochka.rss.db.FieldsConversionService;
import com.tochka.rss.db.NewsURL_Service;

public class SubscriptionControllerTest {
	
	@Test
	public void test() throws Exception {
		NewsURL_Service mockNewsURL_Service = mock(NewsURL_Service.class);
		FieldsConversionService conversionRepo = mock(FieldsConversionService.class);
		
		
		SubscriptionController controller = new SubscriptionController(mockNewsURL_Service, conversionRepo);
		    MockMvc mockMvc = standaloneSetup(controller).build();
		    mockMvc.perform(get("/new_sub")).andExpect(view().name("subscription"));
	}
}
