package com.tochka.rss.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tochka.rss.conversion.FieldsConversionRule;
import com.tochka.rss.db.FieldsConversionService;


@Controller
public class FieldsConversionController {
	private FieldsConversionService conversionRepo;
	
	@Autowired
	public FieldsConversionController(FieldsConversionService conversionRepo) {
		this.conversionRepo = conversionRepo;
	}
	
	@RequestMapping(value ="/conversion",  method=RequestMethod.POST)
	public String saveNewConversion(FieldsConversionRule conversion) {
		conversionRepo.save(conversion);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/conversion", method=RequestMethod.GET)
	public String getSubscriptionForm(Map<String,Object> model) {
		model.put("conversion_rule", new FieldsConversionRule());
		return "conversionRule";
	}
	
	@RequestMapping(value ="/conversion",  params={"addField"}, method=RequestMethod.POST)
	public String addField(FieldsConversionRule conversion, Map<String,Object> model) {
		conversion.addField();
		model.put("conversion_rule", conversion);
		return "conversionRule";
	}
	
	@RequestMapping(value ="/conversion",  params={"removeField"}, method=RequestMethod.POST)
	public String removeField(FieldsConversionRule conversion, Map<String,Object> model, HttpServletRequest req) {
		final Integer fieldId = Integer.valueOf(req.getParameter("removeField"));
		conversion.removeField(fieldId.intValue());
		model.put("conversion_rule", conversion);
		return "conversionRule";
	}
}
