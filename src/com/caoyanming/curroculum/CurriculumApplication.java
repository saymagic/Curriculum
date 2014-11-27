package com.caoyanming.curroculum;

import android.app.Application;

import com.caoyanming.curriculum.exception.CrashHandler;
/**
 * 
 * @author saymagic
 *
 */
public class CurriculumApplication extends Application {

	public CurriculumApplication() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
        CrashHandler.getInstance().init(this);
	}
	
}
