package com.sogou.code.vo;

public class Attribute {

	private String comment;

	private String field;

	public Attribute(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public Attribute(String name, String type, String field, String comment) {
		this.name = name;
		this.type = type;
		this.field = field;
		this.comment = comment;
	}

	private String name;

	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
