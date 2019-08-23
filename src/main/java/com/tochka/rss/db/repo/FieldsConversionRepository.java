package com.tochka.rss.db.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tochka.rss.conversion.FieldsConversionRule;

public interface FieldsConversionRepository extends CrudRepository<FieldsConversionRule, Long> {
	List<FieldsConversionRule> findAll();
}
