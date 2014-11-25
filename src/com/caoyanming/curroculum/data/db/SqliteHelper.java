package com.caoyanming.curroculum.data.db;

import com.caoyanming.util.TimeUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author saymagic
 *
 */
public class SqliteHelper extends SQLiteOpenHelper {

	private static String INFONAME;
	private static String NOTEPAD_NAME;
	private static String NOTEBOOK_NAME;
	private static int VERSION = 1;


	static
	{
		NOTEPAD_NAME = "notepad";
		NOTEBOOK_NAME = "botebook";
		INFONAME = "curroculum.db";
	}

	public SqliteHelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
	{
		super(paramContext, INFONAME, null, VERSION);
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase)
	{
		paramSQLiteDatabase.execSQL("create table " + NOTEBOOK_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,date TEXT)");
		paramSQLiteDatabase.execSQL("create table " + NOTEPAD_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,date TEXT,content TEXT,notebookid TEXT)");
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
	{

	}
}
