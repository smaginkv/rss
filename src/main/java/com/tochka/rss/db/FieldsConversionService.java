package com.tochka.rss.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tochka.rss.conversion.FieldsConversionRule;
import com.tochka.rss.db.repo.FieldsConversionRepository;

@Service
public class FieldsConversionService {
	private FieldsConversionRepository repository;

	@Autowired	
	public FieldsConversionService(FieldsConversionRepository repository) {
		this.repository = repository;
	}
	
	public FieldsConversionRule save(FieldsConversionRule rule) {
		repository.save(rule);
		return rule;
	}
	
	public List<FieldsConversionRule> findAll() {
		return repository.findAll();
	}
}
