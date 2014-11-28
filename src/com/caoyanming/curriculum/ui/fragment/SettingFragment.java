package com.caoyanming.curriculum.ui.fragment;

import java.sql.DatabaseMetaData;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.caoyanming.curriculum.R;
import com.caoyanming.curriculum.data.DataManager;
import com.caoyanming.curriculum.data.bean.Course;
import com.caoyanming.curriculum.ui.AlertWindow;
import com.caoyanming.curriculum.ui.UIUtils;
import com.caoyanming.curriculum.ui.activity.MainActivity;
import com.caoyanming.util.T;
import com.j256.ormlite.table.TableUtils;
/**
 * 
 * @author saymagic
 *
 */
public class SettingFragment extends BaseFragment {

	private LinearLayout layout;
	private ListView list;
	private MainActivity mainActivity;
	private LinearLayout pref_feedback_ll,pref_about_ll;
	private Button generateTestClass;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		layout = (LinearLayout) inflater.inflate(R.layout.setting_layout,
				container, false);
		mainActivity = (MainActivity) getActivity();
		initView();
		return layout;
	}

	@Override
	public void onResume() {
		super.onResume();
		mainActivity.setTitle("设置");
	}
	
	private void initView() {
		pref_feedback_ll = (LinearLayout) layout.findViewById(R.id.pref_feedback_ll);
		pref_about_ll = (LinearLayout) layout.findViewById(R.id.pref_about_ll);
		generateTestClass = (Button) layout.findViewById(R.id.generate_test_classes);
		OnViewClickListener clickListener = new OnViewClickListener();
		pref_about_ll.setOnClickListener(clickListener);
		pref_feedback_ll.setOnClickListener(clickListener);
		generateTestClass.setOnClickListener(clickListener);
	}
	
	private class OnViewClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.pref_feedback_ll:
				mainActivity.switchContent(new FeedbackFragment());
				break;
			case R.id.pref_about_ll:
				mainActivity.switchContent(new AboutFragment());
				break;
			case R.id.generate_test_classes:
				
				UIUtils.showAlertWindowWithOKCancel(mainActivity, false, null, "生成测试课表将会删除当前所有课表，确定继续？", new AlertWindow.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DataManager.getDataManager(mainActivity).clearCourse();
						DataManager.getDataManager(mainActivity).addCourse(new Course("计算机网络原理", "世纪楼401", 1, 1,2, "廖志方"));
						DataManager.getDataManager(mainActivity).addCourse(new Course("操作系统原理", "世纪楼404", 1, 5,2, "胡志刚"));
						DataManager.getDataManager(mainActivity).addCourse(new Course("自习", "世纪楼402", 1, 9,4, ""));
						DataManager.getDataManager(mainActivity).addCourse(new Course("大型数据库技术", "世纪楼401", 2, 3,2, "谭长庚"));
						DataManager.getDataManager(mainActivity).addCourse(new Course("linux基础", "世纪楼301", 2, 7,2, "宋铁"));
						DataManager.getDataManager(mainActivity).addCourse(new Course("自习", "世纪楼402", 2, 9,4, ""));

						DataManager.getDataManager(mainActivity).addCourse(new Course("计算机网络原理", "世纪楼401", 3, 5,2, "廖志方"));
						DataManager.getDataManager(mainActivity).addCourse(new Course("linux基础", "世纪楼401", 3, 1,2, "宋铁"));
						DataManager.getDataManager(mainActivity).addCourse(new Course("自习", "世纪楼402", 3, 9,4, ""));

						
						DataManager.getDataManager(mainActivity).addCourse(new Course("操作系统原理", "世纪楼404", 4, 5,2, "胡志刚"));
						DataManager.getDataManager(mainActivity).addCourse(new Course("设计模式", "世纪楼402", 4, 1,2, "刘伟"));
						DataManager.getDataManager(mainActivity).addCourse(new Course("自习", "世纪楼402", 4, 9,4, ""));

						DataManager.getDataManager(mainActivity).addCourse(new Course("设计模式", "世纪楼402", 5, 1,2, "刘伟"));
						DataManager.getDataManager(mainActivity).addCourse(new Course("自习", "世纪楼402", 5, 9,4, ""));

						DataManager.getDataManager(mainActivity).addCourse(new Course("自习", "世纪楼402", 6, 9,4, ""));

						UIUtils.dismissAlertWindow();
					}
				}, new AlertWindow.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						UIUtils.dismissAlertWindow();
					}
				});
				break;
			default:
				break;
			}
		}}
	
}
