package com.caoyanming.curroculum.data.db;

import java.sql.SQLException;

import android.content.Context;

import com.caoyanming.curroculum.data.bean.Course;
import com.j256.ormlite.dao.Dao;

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
	 * 增加一个用户
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void add(Course course) 
	{
		/*//事务操作
		TransactionManager.callInTransaction(helper.getConnectionSource(),
				new Callable<Void>()
				{

					@Override
					public Void call() throws Exception
					{
						return null;
					}
				});*/
		try
		{
			courseDaoOpe.create(course);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

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

}
