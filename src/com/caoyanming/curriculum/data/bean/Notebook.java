package com.caoyanming.curriculum.data.bean;

import java.io.Serializable;
import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author saymagic
 *
 */
@DatabaseTable(tableName = "tb_notebook")
public class Notebook  implements Serializable
{
	@DatabaseField(columnName = "date")
	public String date;
	@DatabaseField(generatedId = true)
	public int id;
	@DatabaseField(columnName = "title")
	public String title;
	@ForeignCollectionField
	private Collection<Note> note;
	public String getDate() {
		return date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Collection<Note> getNote() {
		return note;
	}
	public void setNote(Collection<Note> note) {
		this.note = note;
	}
	public void setData(String date) {
		this.date = date;
	}
	public int getid() {
		return id;
	}
	public void setid(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}




}
