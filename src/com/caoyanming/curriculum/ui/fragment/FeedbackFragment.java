package com.caoyanming.curriculum.ui.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.caoyanming.curriculum.Config;
import com.caoyanming.curriculum.R;
import com.caoyanming.curriculum.custom.JsonObjectUTF8Request;
import com.caoyanming.curriculum.ui.AlertWindow;
import com.caoyanming.curriculum.ui.UIUtils;
import com.caoyanming.curriculum.ui.activity.MainActivity;
import com.caoyanming.util.T;
/**
 * 
 * @author saymagic
 *
 */
public class FeedbackFragment extends BaseFragment {

	private LinearLayout layout;
	private ListView list;
	private MainActivity mainActivity;
	private EditText prefFeedbacket;
	private Button prefSendFeedback;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = (LinearLayout) inflater.inflate(R.layout.pref_feedback_fragment,
				container, false);
		mainActivity = (MainActivity) getActivity();
		initView();
		return layout;
	}

	@Override
	public void onResume() {
		super.onResume();
		mainActivity.setTitle("反馈");
	}

	private void initView() {
		prefFeedbacket = (EditText) layout.findViewById(R.id.pref_feedback_info);
		prefSendFeedback = (Button) layout.findViewById(R.id.feed_back);
		//采用邮件的方式进行反馈，方便观看。
		prefSendFeedback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String feedback = prefFeedbacket.getText().toString();
				if(TextUtils.isEmpty(feedback)){
					T.showLong(mainActivity, "你的反馈意见为空");
				}else if(feedback.length() > 950){
					T.showLong(mainActivity, "你的反馈过长");
				}else{
					String msg = "";
					try {
						msg = URLEncoder.encode(feedback, "utf-8");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					} 
					RequestQueue mQueue = Volley.newRequestQueue(mainActivity);
					StringRequest sReq = new StringRequest(Config.FEEDBACK_URL+"?msg="+msg, new Listener<String>() {
						@Override
						public void onResponse(String res) {
							if(res.equals("DONE")){
								T.showLong(mainActivity, "反馈成功");
							}else if(res.equals("ILLEGAL")){
								UIUtils.showAlertWindowWithOK(mainActivity, true, "反馈失败", "你的反馈内容过长。", new AlertWindow.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										UIUtils.dismissAlertWindow();
									}
								});							
							}else{
									UIUtils.showAlertWindowWithOK(mainActivity, true, "反馈失败", "啊哦，可能网络原因，反馈失败。", new AlertWindow.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {
											UIUtils.dismissAlertWindow();
										}
									});
								}

						}
					}, new ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError arg0) {
							UIUtils.showAlertWindowWithOK(mainActivity, true, "反馈失败", "啊哦，可能网络原因，反馈失败。", new AlertWindow.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									UIUtils.dismissAlertWindow();
								}
							});
						}
					});
					
					mQueue.add(sReq);
					mQueue.start();
				}
			}
		});
	}


}
