package com.caoyanming.curroculum.data.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author saymagic
 *
 */

@DatabaseTable(tableName = "tb_course")
public class Course {

	public Course() {}
	
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
	private String weekly;
	//开始的节数
	@DatabaseField(columnName = "startClass")
	private int startClass;
	//该课程的总共节数
	@DatabaseField(columnName = "classes")
	private int classes;
	//背景色
	@DatabaseField(columnName = "color")
	private int color;
	
	
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
	public String getWeekly() {
		return weekly;
	}
	public void setWeekly(String weekly) {
		this.weekly = weekly;
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