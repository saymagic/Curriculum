package com.caoyanming.curriculum.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.caoyanming.curriculum.R;
import com.caoyanming.curriculum.ui.activity.MainActivity;
import com.caoyanming.util.APPVersion;
import com.caoyanming.util.T;
/**
 * 
 * @author saymagic
 *
 */
public class AboutFragment extends BaseFragment {

	private RelativeLayout layout;
	private ListView list;
	private MainActivity mainActivity;
	private Button checkUpdate;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = (RelativeLayout) inflater.inflate(R.layout.pref_about_fragment,
				container, false);
		mainActivity = (MainActivity) getActivity();
		initView();
		return layout;
	}

	@Override
	public void onResume() {
		super.onResume();
		mainActivity.setTitle("关于");
	}
	
	private void initView() {
		checkUpdate = (Button) layout.findViewById(R.id.check_update);
		OnViewClickListener clickListener = new OnViewClickListener();
		checkUpdate.setOnClickListener(clickListener);
	}
	
	private class OnViewClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.pref_feedback_ll:
				break;
			case R.id.pref_about_ll:
				break;
			case R.id.check_update:
				new APPVersion(mainActivity).needUpdate();
				break;
			default:
				break;
			}
		}}
	
}
