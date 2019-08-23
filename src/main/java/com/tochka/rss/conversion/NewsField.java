package com.tochka.rss.conversion;

public class NewsField {
	private final String fieldsName;
	private String value;

	public NewsField(String fieldsName) {
		if(fieldsName.isEmpty()) {
			this.fieldsName = "";
			
		}else if (fieldsName.length() == 1){
			this.fieldsName = fieldsName.toUpperCase();
			
		}else {
			this.fieldsName = fieldsName.substring(0, 1).toUpperCase() + fieldsName.substring(1);
		}
		this.value = "";
	}

	public String getFieldsName() {
		return fieldsName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
