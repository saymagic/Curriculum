package com.caoyanming.curroculum.ui.activity;

import java.io.File;

import com.caoyanming.curriculum.R;
import com.caoyanming.curriculum.R.layout;
import com.caoyanming.util.HttpUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

/**
 * 
 * @author saymagic
 *
 * 发送crash的activity。该activity是在崩溃后自动重启的。
 */
public class SendCrashActivity extends BaseActivity {


	private static final String uploadUrl = "http://3.saymagic.sinaapp.com/ReceiveCrash.php";

	private ProgressDialog progressDialog;
	/**
	 * localFileUrl
	 * 本地log文件的存放地址
	 */
	private static String localFileUrl = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_send_crash);
		setTitle("崩溃收集");
		//这里把刚才异常堆栈信息写入SD卡的Log日志里面
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) 
		{
			String sdcardPath = Environment.getExternalStorageDirectory().getPath();
			localFileUrl = sdcardPath + "/caoyanming/curriculum/crash/crash.log";
		}
	}

	public void sendCrash(View view){
		progressDialog = new ProgressDialog(SendCrashActivity.this);
		progressDialog.show(SendCrashActivity.this, "请稍等", "正在上传崩溃信息");
		new SendCrashLog().execute("");

	}
	/**
	 * 
	 * @author saymagic
	 * 向服务器发送崩溃信息
	 */
	public class SendCrashLog extends AsyncTask<String, String, Boolean> 
	{
		public SendCrashLog() {  }

		@Override
		protected Boolean doInBackground(String... params) 
		{
			Log.d("TAG", "向服务器发送崩溃信息");
			HttpUtils.uploadFile(new File(localFileUrl), uploadUrl);
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			progressDialog.dismiss();
			Toast.makeText(getApplicationContext(), "成功将崩溃信息发送到服务器，应用自动重启，感谢您的反馈", 5000).show();
			Intent i = new Intent(SendCrashActivity.this,MainActivity.class);
			startActivity(i);
			SendCrashActivity.this.finish();
			Log.d("TAG", "发送完成");
			
		}
	}
}
