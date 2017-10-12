/**
 * 
 */
package com.sogou.code.vo;

/**
 * @author liuquan
 *
 */
public class ColumnVO {

	private String tableName;
	private String type;
	private String name;//驼峰名称,首字母大写
	private String dbField;///数据库字段
	private String field;//驼峰名称,首字母小写
	private String comment;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getDbField() {
		return dbField;
	}

	public void setDbField(String dbField) {
		this.dbField = dbField;
	}
	
	

}
