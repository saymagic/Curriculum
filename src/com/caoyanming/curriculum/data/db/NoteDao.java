package com.caoyanming.curriculum.data.db;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.caoyanming.curriculum.data.bean.Note;
import com.j256.ormlite.dao.Dao;
/**
 * 
 * @author saymagic
 *
 */
public class NoteDao
{
	private Context context;
	private Dao<Note, Integer> noteDaoOpe;
	private DatabaseHelper helper;

	public NoteDao(Context context)
	{
		this.context = context;
		try
		{
			helper = DatabaseHelper.getHelper(context);
			noteDaoOpe = helper.getDao(Note.class);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 增加一门课程
	 * 
	 * @param note
	 * @throws SQLException
	 */
	public void add(Note note) 
	{

		try
		{
			noteDaoOpe.create(note);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}
	
	
	
	public void delete(Note note){
		try {
			noteDaoOpe.delete(note);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update(Note note){
		try {
			noteDaoOpe.update(note);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<Note> queryAllNote(){
		try {
			return noteDaoOpe.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public Note get(int id)
	{
		try
		{
			return noteDaoOpe.queryForId(id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
