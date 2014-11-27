package com.caoyanming.curroculum.data.bean;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author saymagic
 *
 */

@DatabaseTable(tableName = "tb_course")
public class Course implements Serializable{

	public Course() {}

	public Course(int week,int startClass){
		this.weekly = week;
		this.startClass = startClass;
	}

	@DatabaseField(generatedId = true)
	private int id;
	// 课程名称
	@DatabaseField(columnName = "title")
	private String title;
	//地点
	@DatabaseField(columnName = "place")
	private String place;
	//上课时间
	@DatabaseField(columnName = "time")
	private String time;
	//周次
	@DatabaseField(columnName = "weekly")
	private int weekly;
	//开始的节数
	@DatabaseField(columnName = "startClass")
	private int startClass;
	//该课程的总共节数
	@DatabaseField(columnName = "classes")
	private int classes;
	//背景色
	@DatabaseField(columnName = "color")
	private int color;
	//老师
	@DatabaseField(columnName = "teacher")
	private String teacher;
	//@DatabaseField(canBeNull = true, foreign = true, columnName = "notebook")  
	public Notebook notebook;
	
	private boolean isBlank;

	public Notebook getNotebook() {
		return notebook;
	}

	public void setNotebook(Notebook notebook) {
		this.notebook = notebook;
	}

	public boolean isBlank() {
		return isBlank;
	}

	public void setBlank(boolean isBlank) {
		this.isBlank = isBlank;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getWeekly() {
		return weekly;
	}
	public void setWeekly(int i) {
		this.weekly = i%7;
	}
	public int getStartClass() {
		return startClass;
	}
	public void setStartClass(int startClass) {
		this.startClass = startClass;
	}
	public int getClasses() {
		return classes;
	}
	public void setClasses(int classes) {
		this.classes = classes;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}





}
