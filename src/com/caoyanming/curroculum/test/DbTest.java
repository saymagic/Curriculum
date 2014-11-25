package com.caoyanming.curroculum.test;

import android.test.AndroidTestCase;

import com.caoyanming.curroculum.data.DataManager;
import com.caoyanming.curroculum.data.bean.Course;
import com.caoyanming.curroculum.data.db.CourseDao;
import com.caoyanming.util.L;
import com.j256.ormlite.dao.DaoManager;

public class DbTest extends AndroidTestCase{

	public DbTest() {

	}
	
	public void testAddCourse(){
		Course c = new Course();
		c.setClasses(2);
		c.setColor(1);
		c.setPlace("中南");
		c.setStartClass(3);
		c.setTime("3:10-4:20");
		c.setTitle("数字电路");
		c.setWeekly(2);
		new CourseDao(getContext()).add(c);
	}
	
	
	public void testQuerryAllCourse(){
		L.e(DataManager.getDataManager(getContext()).getAllCourse().toString());
		//L.d(new CourseDao(getContext()).queryAllCourse().toString());
	}

}
