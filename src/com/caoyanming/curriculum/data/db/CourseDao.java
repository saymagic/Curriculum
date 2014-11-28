package com.caoyanming.curriculum.data.db;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.caoyanming.curriculum.data.bean.Course;
import com.j256.ormlite.dao.Dao;
/**
 * 
 * @author saymagic
 *
 */
public class CourseDao
{
	private Context context;
	private Dao<Course, Integer> courseDaoOpe;
	private DatabaseHelper helper;

	public CourseDao(Context context)
	{
		this.context = context;
		try
		{
			helper = DatabaseHelper.getHelper(context);
			courseDaoOpe = helper.getDao(Course.class);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 增加一门课程
	 * 
	 * @param course
	 * @throws SQLException
	 */
	public void add(Course course) 
	{

		try
		{
			courseDaoOpe.create(course);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}
	
	
	
	public void delete(Course course){
		try {
			courseDaoOpe.delete(course);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update(Course course){
		try {
			courseDaoOpe.update(course);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<Course> queryAllCourse(){
		try {
			return courseDaoOpe.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public Course get(int id)
	{
		try
		{
			return courseDaoOpe.queryForId(id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void clearCourse() {
		helper.clearTable(Course.class);
	}

}
