package com.caoyanming.curroculum.data;

import java.util.List;

import android.content.Context;

import com.caoyanming.curroculum.data.bean.Course;
import com.caoyanming.curroculum.data.db.CourseDao;

/**
 * 
 * @author saymagic
 *
 */
public class DataManager {

	private static DataManager instance;
	private CourseDao courseDao;
	private Context context;
	private DataManager(Context context) {
		this.context = context;
		courseDao = new CourseDao(context);
	}

	/**
	 * 单例获取该DataManager
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized DataManager getDataManager(Context context)
	{
		if (instance == null)
		{
			synchronized (DataManager.class)
			{
				if (instance == null)
					instance = new DataManager(context);
			}
		}

		return instance;
	}
	
	
	public List<Course> getAllCourse(){
		if(courseDao == null)
			courseDao = new CourseDao(context);
		return  courseDao.queryAllCourse();
	}

}
