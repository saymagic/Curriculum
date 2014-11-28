package com.caoyanming.curriculum.data.db;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.caoyanming.curriculum.data.bean.Notebook;
import com.j256.ormlite.dao.Dao;
/**
 * 
 * @author saymagic
 *
 */
public class NotebookDao
{
	private Context context;
	private Dao<Notebook, Integer> notebookDaoOpe;
	private DatabaseHelper helper;

	public NotebookDao(Context context)
	{
		this.context = context;
		try
		{
			helper = DatabaseHelper.getHelper(context);
			notebookDaoOpe = helper.getDao(Notebook.class);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 增加一门课程
	 * 
	 * @param notebook
	 * @throws SQLException
	 */
	public void add(Notebook notebook) 
	{

		try
		{
			notebookDaoOpe.create(notebook);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}
	
	
	
	public void delete(Notebook notebook){
		try {
			notebookDaoOpe.delete(notebook);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update(Notebook notebook){
		try {
			notebookDaoOpe.update(notebook);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<Notebook> queryAllNotebook(){
		try {
			return notebookDaoOpe.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public Notebook get(int id)
	{
		try
		{
			return notebookDaoOpe.queryForId(id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Notebook> get(String title){
		try {
			return notebookDaoOpe.queryBuilder().where().eq("title",title).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
