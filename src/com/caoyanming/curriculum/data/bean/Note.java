package com.caoyanming.curriculum.data.bean;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * 
 * @author saymagic
 *
 */
@DatabaseTable(tableName = "tb_note")
public class Note implements Serializable{

	@DatabaseField(generatedId = true)
	public int id;
	@DatabaseField(columnName = "content")
	public String content;
	@DatabaseField(columnName = "date")
	public String date;
	@DatabaseField(columnName = "title")
	public String title;
	@DatabaseField(columnName = "expanded")
	private boolean  expanded;
	@DatabaseField(canBeNull = true, foreign = true, columnName = "notebook")  
	public Notebook notebook;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public Notebook getNotebook() {
		return notebook;
	}
	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
	}
	

}
