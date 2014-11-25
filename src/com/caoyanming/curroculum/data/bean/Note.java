package com.caoyanming.curroculum.data.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_note")
public class Note {

	@DatabaseField(generatedId = true)
	public int id;
	@DatabaseField(columnName = "content")
	public String content;
	@DatabaseField(columnName = "date")
	public String date;
	@DatabaseField(columnName = "title")
	public String title;
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
	public void setData(String date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Notebook getNotebook() {
		return notebook;
	}
	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
	}


}
