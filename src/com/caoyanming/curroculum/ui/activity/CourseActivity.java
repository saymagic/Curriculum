package com.caoyanming.curroculum.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.caoyanming.curriculum.R;
import com.caoyanming.curroculum.data.DataManager;
import com.caoyanming.curroculum.data.bean.Course;
import com.caoyanming.util.T;

public class CourseActivity extends BaseActivity {

	private EditText courseName,coursePlace,courseClasses,courseTeacher;
	private Button addBtn;
	private Course course;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_course);
		if((Course) getIntent().getSerializableExtra("course") != null){
			course = (Course) getIntent().getSerializableExtra("course");
		}
		else {
			course = new Course();
		}
		initView();
		setListeners();
	}
	private void setListeners() {
		addBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String title = courseName.getText().toString();
				if(TextUtils.isEmpty(title)){
					T.showLong(CourseActivity.this, getResources().getString(R.string.course_title_illegal));
					return;
				}
				String place = coursePlace.getText().toString();
				if(TextUtils.isEmpty(place)){
					T.showLong(CourseActivity.this, getResources().getString(R.string.course_place_illegal));
					return;
				}
				String classes = courseClasses.getText().toString();
				if(!TextUtils.isDigitsOnly(classes) || TextUtils.isEmpty(classes)){
					T.showLong(CourseActivity.this, getResources().getString(R.string.course_classes_illegal));
					return;
				}else{
					if(Integer.valueOf(classes)+course.getStartClass() > 15 ){
						T.showLong(CourseActivity.this, getResources().getString(R.string.course_class_toolong) );
						return;
					}else if(Integer.valueOf(classes) < 0){
						T.showLong(CourseActivity.this, getResources().getString(R.string.course_class_toolong) );
						return;
					}else if(Integer.valueOf(classes) == 0){
						T.showLong(CourseActivity.this, getResources().getString(R.string.course_class_zero) );
						return;
					}
				}
				String teracher = TextUtils.isEmpty(courseTeacher.getText().toString())? "" :courseTeacher.getText().toString();
				course.setTitle(title);
				course.setClasses(Integer.valueOf(classes));
				course.setPlace(place);
				//随机生成一种颜色
				if(course.getColor() == 0)
					course.setColor((int)(1+Math.random()*7));
				course.setTeacher(teracher);
				if(DataManager.getDataManager(CourseActivity.this).isCourseConfict(course)){
					T.showLong(CourseActivity.this, getResources().getString(R.string.course_class_conflict) );
					return;
				}
				DataManager.getDataManager(CourseActivity.this).addOrUpdateCourse(course);
				CourseActivity.this.finish();
			}
		});
	}
	private void initView() {
		addBtn = (Button) findViewById(R.id.btn_add_course);
		courseName = (EditText) findViewById(R.id.course_name);
		coursePlace = (EditText) findViewById(R.id.et_course_place);
		courseClasses = (EditText) findViewById(R.id.course_classes);
		courseTeacher = (EditText) findViewById(R.id.course_teacher);
		//如果当前课程不是空白，表示正在修改课程信息，我们需要人性化的将每个et添上相应的值。
		if(!course.isBlank()){
			courseName.setText(course.getTitle());
			coursePlace.setText(course.getPlace());
			courseClasses.setText(String.valueOf(course.getClasses()));
			courseTeacher.setText(course.getTeacher());
			setTitle(getResources().getString(R.string.fix_course));
		}else{
			setTitle(getResources().getString(R.string.add_course));
		}
			
	}


}
