package com.caoyanming.curroculum.data.db;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author saymagic
 *
 */
public interface ChangeSqliteAbstract {
	
	long add(SQLiteDatabase paramSQLiteDatabase, Object param);
	
	void delete(SQLiteDatabase paramSQLiteDatabase, Object param);
	
	ArrayList query(SQLiteDatabase paramSQLiteDatabase);
	
	 void update(SQLiteDatabase paramSQLiteDatabase, Object param) ;

}
