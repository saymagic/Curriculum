package com.caoyanming.curroculum.data;

import java.util.List;

import android.content.Context;

import com.caoyanming.curroculum.data.bean.Course;
import com.caoyanming.curroculum.data.bean.Note;
import com.caoyanming.curroculum.data.bean.Notebook;
import com.caoyanming.curroculum.data.db.CourseDao;
import com.caoyanming.curroculum.data.db.NoteDao;
import com.caoyanming.curroculum.data.db.NotebookDao;
import com.caoyanming.util.CollectionUtil;

/**
 * 
 * @author saymagic
 *
 */
public class DataManager {

	private static DataManager instance;
	private CourseDao courseDao;
	private NoteDao noteDao;
	private NotebookDao notebookDao;
	private Context context;
	private DataManager(Context context) {
		this.context = context;
		courseDao = new CourseDao(context);
		noteDao  = new NoteDao(context);
		notebookDao = new NotebookDao(context);
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
	
	
	public Course getCourseByID(int id){
		return courseDao.get(id);
	}
	
	public void addCourse(Course course){
		courseDao.add(course);
	}
	
	public void deleteCourse(Course course){
		courseDao.delete(course);
	}
	
	public void updateCourse(Course course){
		courseDao.update(course);
	}
	
	public void addOrUpdateCourse(Course course){
		if(getCourseByID(course.getId()) == null)
			addCourse(course);
		else
			updateCourse(course);
	}
	
	public List<Course> getAllCourse(){
		return  courseDao.queryAllCourse();
	}
	
	public Note getNoteByID(int id){
		return noteDao.get(id);
	}
	
	public void addNote(Note note){
		noteDao.add(note);
	}
	
	public void deleteNote(Note note){
		noteDao.delete(note);
	}
	
	public void updateNote(Note note){
		noteDao.update(note);
	}
	
	public List<Note> getAllNote(){
		return  noteDao.queryAllNote();
	}

	public Notebook getNotebookByID(int id){
		return notebookDao.get(id);
	}
	
	public void addNotebook(Notebook notebook){
		notebookDao.add(notebook);
	}
	
	public void deleteNotebook(Notebook notebook){
		notebookDao.delete(notebook);
	}
	
	public void updateNotebook(Notebook notebook){
		notebookDao.update(notebook);
	}
	
	public List<Notebook> getAllNotebook(){
		return  notebookDao.queryAllNotebook();
	}
	
	public Notebook getOrCreateNotebookByTitle(String title){
		List<Notebook> notebooks = notebookDao.get(title);
		if(CollectionUtil.isListEmpty(notebooks)){
			Notebook notebook = new Notebook();
			notebook.setTitle(title);
			addNotebook(notebook);
			return notebook;
		}else{
			return notebooks.get(0);
		}
	}

}
