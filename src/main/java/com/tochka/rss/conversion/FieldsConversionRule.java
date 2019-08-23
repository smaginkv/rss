package com.tochka.rss.conversion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class FieldsConversionRule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String title;
	
	@Column
	private String itemTag;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable
	private List<FieldConversion> fields = new ArrayList<>();
	
	public String getTitle() {
		return title;
	}

	public String getItemTag() {
		return itemTag;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setItemTag(String itemTag) {
		this.itemTag = itemTag;
	}
	
	public Map<String, NewsField> getFieldsMap(){
		HashMap<String, NewsField> map = new HashMap<>();
		for(FieldConversion field: fields) {
			map.put(field.getXmlNodeName(), new NewsField(field.getFieldsName()));
		}
		return map;
	}
	
	public List<FieldConversion> getFields() {
		return fields;
	}
	
	public void addField() {
		fields.add(new FieldConversion());
	}
	
	public void removeField(int id) {
		fields.remove(id);
	}
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
