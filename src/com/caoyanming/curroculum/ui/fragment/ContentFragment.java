package com.caoyanming.curroculum.ui.fragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caoyanming.curriculum.R;
import com.caoyanming.curroculum.data.DataManager;
import com.caoyanming.curroculum.data.bean.Course;
import com.caoyanming.curroculum.data.db.CourseDao;
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
	void setClass(LinearLayout ll, String title, String place,
			String last, String time, int classes, int color)
	{
		View view = LayoutInflater.from(mainActivity).inflate(R.layout.course_item, null);
		view.setMinimumHeight(dip2px(mainActivity,classes * 48));
		view.setBackgroundColor(colors[color]);
		((TextView)view.findViewById(R.id.title)).setText(title);
		((TextView)view.findViewById(R.id.place)).setText(place);
		((TextView)view.findViewById(R.id.last)).setText(last);
		((TextView)view.findViewById(R.id.time)).setText(time);
		//为课程View设置点击的监听器
		view.setOnClickListener(new OnClickClassListener());
		TextView blank1 = new TextView(mainActivity);
		TextView blank2 = new TextView(mainActivity);
		blank1.setHeight(dip2px(mainActivity,classes));
		blank2.setHeight(dip2px(mainActivity,classes));
		ll.addView(blank1);
		ll.addView(view);
		ll.addView(blank2);
	}
	/**
	 * 设置无课（空百）
	 * @param ll
	 * @param classes 无课的节数（长度）
	 * @param color
	 */
	void setNoClass(LinearLayout ll,int classes, int color)
	{
		TextView blank = new TextView(mainActivity);
		if(color == 0)
			blank.setMinHeight(dip2px(mainActivity,classes * 50));
		blank.setBackgroundColor(colors[color]);
		ll.addView(blank);

	}
	//点击课程的监听器
	class OnClickClassListener implements OnClickListener{

		public void onClick(View v) {
			// TODO Auto-generated method stub
			String title;
			title = (String) ((TextView)v.findViewById(R.id.title)).getText();
			Toast.makeText(mainActivity, "你点击的是:" + title, 
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
		T.showLong(mainActivity, courseList.toString());

		if(CollectionUtil.isListEmpty(courseList))
			setAllNoClass();
		else
			orderClasses(courseList);

	}

	private void orderClasses(List courseList) {
		//T.showLong(mainActivity, courseList.toString());
		setAllNoClass();
	}

	//全部设成无课
	private void setAllNoClass(){
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 14; j++){
				setNoClass(linearLayoutList.get(i),1,0);
			}
		}
	}

}
