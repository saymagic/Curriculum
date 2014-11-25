package com.caoyanming.curroculum.ui.fragment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caoyanming.curriculum.R;
import com.caoyanming.curroculum.data.DataManager;
import com.caoyanming.curroculum.data.bean.Course;
import com.caoyanming.curroculum.ui.AlertWindow;
import com.caoyanming.curroculum.ui.UIUtils;
import com.caoyanming.curroculum.ui.activity.MainActivity;
import com.caoyanming.util.CollectionUtil;
import com.caoyanming.util.T;
/**
 * 
 * @author saymagic
 *
 */

public class ContentFragment extends BaseFragment {

	private View layout;
	private ArrayList<LinearLayout> linearLayoutList ;
	//分别表示周一到周日
	private LinearLayout ll1;
	private LinearLayout ll2;
	private LinearLayout ll3;
	private LinearLayout ll4;
	private LinearLayout ll5;
	private LinearLayout ll6;
	private LinearLayout ll7;
	private ArrayList<Course> courseList;
	private MainActivity mainActivity;

	private int colors[] = {
			Color.rgb(0xee,0xff,0xff),
			Color.rgb(0xf0,0x96,0x09),
			Color.rgb(0x8c,0xbf,0x26),
			Color.rgb(0x00,0xab,0xa9),
			Color.rgb(0x99,0x6c,0x33),
			Color.rgb(0x3b,0x92,0xbc),
			Color.rgb(0xd5,0x4d,0x34),
			Color.rgb(0xcc,0xcc,0xcc)
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = inflater.inflate(R.layout.course_layout, container, false);
		mainActivity = (MainActivity) getActivity();
		ll1 = (LinearLayout)layout.findViewById(R.id.ll1);
		ll2 = (LinearLayout)layout.findViewById(R.id.ll2);
		ll3 = (LinearLayout)layout.findViewById(R.id.ll3);
		ll4 = (LinearLayout)layout.findViewById(R.id.ll4);
		ll5 = (LinearLayout)layout.findViewById(R.id.ll5);
		ll6 = (LinearLayout)layout.findViewById(R.id.ll6);
		ll7 = (LinearLayout)layout.findViewById(R.id.ll7);

		linearLayoutList = new ArrayList<LinearLayout>();

		linearLayoutList.add(ll1);
		linearLayoutList.add(ll2);
		linearLayoutList.add(ll3);
		linearLayoutList.add(ll4);
		linearLayoutList.add(ll5);
		linearLayoutList.add(ll6);
		linearLayoutList.add(ll7);


		refreshCurriculumByDB();

		return layout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		courseList = new ArrayList<Course>();

	}

	/**
	 * 设置课程的方法
	 * @param ll
	 * @param title 课程名称
	 * @param place 地点
	 * @param last 时间
	 * @param time 周次
	 * @param classes 节数
	 * @param color 背景色
	 */
	void setClass(LinearLayout ll, Course course)
	{
		View view = LayoutInflater.from(mainActivity).inflate(R.layout.course_item, null);
		view.setMinimumHeight(dip2px(mainActivity,course.getClasses() * 48));
		view.setBackgroundColor(colors[course.getColor()]);
		((TextView)view.findViewById(R.id.course_item_title)).setText(course.getTitle());
		((TextView)view.findViewById(R.id.course_item_place)).setText(course.getPlace());
		//((TextView)view.findViewById(R.id.course_item_last)).setText(String.valueOf(course.getWeekly()));
		((TextView)view.findViewById(R.id.course_item_time)).setText(String.valueOf(course.getTime()));

		//为课程View设置点击的监听器
		view.setOnClickListener(new OnClickClassListener());
		TextView blank1 = new TextView(mainActivity);
		TextView blank2 = new TextView(mainActivity);
		view.setId(course.getId());
		blank1.setHeight(dip2px(mainActivity,course.getClasses()));
		blank2.setHeight(dip2px(mainActivity,course.getClasses()));
		ll.addView(blank1);
		ll.addView(view);
		ll.addView(blank2);
	}
	/**
	 * 设置无课（空白）
	 * @param ll
	 * @param classes 无课的节数（长度）
	 * @param color
	 * @param week
	 * @param startClass
	 */
	void setNoClass(LinearLayout ll,int classes, int color,Course course)
	{
		TextView blank = new TextView(mainActivity);
		if(color == 0)
			blank.setMinHeight(dip2px(mainActivity,classes * 48));
		blank.setBackgroundColor(colors[color]);
		TextView blank1 = new TextView(mainActivity);
		TextView blank2 = new TextView(mainActivity);
		blank1.setHeight(dip2px(mainActivity,classes));
		blank2.setHeight(dip2px(mainActivity,classes));
		ll.addView(blank2);
		ll.addView(blank);
		ll.addView(blank1);
		blank.setTag(course);
		blank.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView textView = (TextView) v;
				Course c = (Course) textView.getTag();
				T.showLong(mainActivity, String.valueOf(c.getWeekly()));
			}
		});

	}
	//点击课程的监听器
	class OnClickClassListener implements OnClickListener{

		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			Course c = DataManager.getDataManager(mainActivity).getCourseByID(id);
			final String [] menu = getResources().getStringArray(R.array.course_menu);
			UIUtils.showAlertWindowWithList(mainActivity, getResources().getString(R.string.course_menu_title), new ArrayAdapter<String>(mainActivity,  R.layout.alert_window_list_text_item, menu),new  AlertWindow.OnListItemClickListener(){
				@Override
				public void onItemClick(View view, int which) {
					UIUtils.dismissAlertWindow();
					Toast.makeText(mainActivity,menu[which]+"", 1000).show();
				}
			});
			Toast.makeText(mainActivity, "你点击的是:" + c.getTitle(), 
					Toast.LENGTH_SHORT).show();
		}
	}

	public static int dip2px(Context context, float dpValue) {        
		final float scale = context.getResources().getDisplayMetrics().density;        
		return (int) (dpValue * scale + 0.5f);} /** * 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
	public static int px2dip(Context context, float pxValue) {        
		final float scale = context.getResources().getDisplayMetrics().density;        
		return (int) (pxValue / scale + 0.5f);}


	private void refreshCurriculumByDB(){
		List<Course>  courseList = DataManager.getDataManager(mainActivity.getApplicationContext()).getAllCourse();
		if(CollectionUtil.isListEmpty(courseList))
			setAllNoClass();
		else
			orderClasses(courseList);

	}

	private void orderClasses(List courseList) {
		for(int i = 1; i <= 7; i++){
			for(int j = 1; j <= 14; j++){
				Course course = getCourceByWeekAndClass(courseList,i,j);
				if(course == null)
					setNoClass(linearLayoutList.get(i-1),1,0,new Course(i, j));
				else{
					setClass(linearLayoutList.get(i-1), course);
					j += course.getClasses();
				}
			}
		}	
	}

	//全部设成无课
	private void setAllNoClass(){
		for(int i = 1; i <= 7; i++){
			for(int j = 1; j <= 14; j++){
				setNoClass(linearLayoutList.get(i-1),1,0,new Course(i, j));
			}
		}
	}

	private Course getCourceByWeekAndClass(List<Course> courseList,int week,int _class){
		for (Iterator<Course> courseIterator = courseList.iterator(); courseIterator.hasNext();) {  
			Course course = courseIterator.next(); // line 1  
			if(course.getWeekly() == week && (course.getStartClass() <= _class && course.getClasses()+course.getStartClass() <= _class))
				return course;
		}  
		return null;
	}

}
