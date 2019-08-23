package com.tochka.rss.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tochka.rss.conversion.FieldsConversionRule;
import com.tochka.rss.db.FieldsConversionService;
import com.tochka.rss.db.NewsURL_Service;
import com.tochka.rss.domain.NewsURL;
import com.tochka.rss.parsing.Parsable;

@Controller
public class SubscriptionController {

	private final NewsURL_Service newsUrlRepo;
	private final FieldsConversionService conversionRepo;
	
	@Autowired
	public SubscriptionController(NewsURL_Service newsUrlRepo, FieldsConversionService conversionRepo) {
		this.newsUrlRepo = newsUrlRepo;
		this.conversionRepo = conversionRepo;
	}
	
	@RequestMapping(value = "/new_sub", method=RequestMethod.GET)
	public String getSubscriptionForm(Map<String,Object> model) {
		model.put("news_url", new NewsURL());
		return "subscription";
	}
	
	@RequestMapping(value ="/new_sub", params={"addConversion"}, method=RequestMethod.POST)
	public String getNewConversion() {
		return "redirect:/conversion";
	}
	
	@RequestMapping(value ="/new_sub", method=RequestMethod.POST)
	public String saveNewSubscription(NewsURL newsURL) {
		newsUrlRepo.save(newsURL);
		return "redirect:/";
	}
	
	@ModelAttribute("allAvailableRuleClass")
	public List<String> populateAvailableRuleClass() {
		Reflections parsingPackage = new Reflections("com.tochka.rss");
		Set<Class<? extends Parsable>> subTypes = parsingPackage.getSubTypesOf(Parsable.class);
		
		ArrayList<String> namesArray = new ArrayList<>();
		for(Class<? extends Parsable> subType: subTypes) {
			if(subType.isAnnotationPresent(Component.class)) {
				namesArray.add(subType.getAnnotation(Component.class).value());	
			}			
		}
	    return namesArray;
	}
	
	@ModelAttribute("allAvailableConversion")
	public List<FieldsConversionRule> populateAvailableConversion() {
		List<FieldsConversionRule> conversion = conversionRepo.findAll();
	    return conversion;
	}
}