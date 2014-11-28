package com.caoyanming.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.caoyanming.curriculum.Config;
import com.caoyanming.curriculum.R;
import com.caoyanming.curriculum.custom.JsonObjectUTF8Request;
import com.caoyanming.curriculum.ui.AlertWindow.OnClickListener;
import com.caoyanming.curriculum.ui.AlertWindow;
import com.caoyanming.curriculum.ui.UIUtils;

/**
 *
 * @author saymagic
 *
 */
public class APPVersion {
	
	private  String UPDATE_SAVENAME="curriculum.apk";
	Context context;

	public APPVersion(Context context){
		this.context = context;
	}
	private  int getVerCode(){
		int versionCode = -1;
		try {
			versionCode = context.getPackageManager().getPackageInfo(Config.PACKAPGE_INFO, 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e("TAG", e.getMessage());
			e.printStackTrace();
		}
		return versionCode;
	}

	private  String getVerName() {
		String versionName = "";
		try {
			versionName = context.getPackageManager().getPackageInfo(
					Config.PACKAPGE_INFO, 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e("TAG", e.getMessage());
		}
		return versionName;
	}

	private  String getAppName() {
		String verName = context.getResources()
				.getText(R.string.app_name).toString();
		return verName;
	}

	public  void needUpdate(){
		RequestQueue mQueue = Volley.newRequestQueue(context);  
		JsonObjectUTF8Request jReq = new JsonObjectUTF8Request(Config.UPDATE_JSON_URL,null,
				new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					Log.d("TAG", response.toString());
					if(Integer.valueOf(response.get("versionCode").toString())>getVerCode()){
						showUpdateNotice(response);
					}else{
						showNotNewVersion(context);
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				UIUtils.showAlertWindowWithOK(context, true, "反馈失败", "啊哦，可能网络原因，连接服务器失败。", new AlertWindow.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						UIUtils.dismissAlertWindow();
					}
				});
			}
		});
		mQueue.add(jReq);
		mQueue.start();
	}

	public  void showUpdateNotice(final JSONObject jObject) throws JSONException {
		UIUtils.showAlertWindowWithDeleteOnLeft(context, "确定更新", jObject.getString("description"), "是", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					doNewVersionUpdate(jObject.get("downloadUrl").toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				UIUtils.dismissAlertWindow();
			}
		}, "否", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				UIUtils.dismissAlertWindow();
			}
		});
	}

	private  void showNotNewVersion(Context context){
		UIUtils.showAlertWindowWithOK(context, false, null, "当前是最新版本，无需更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				UIUtils.dismissAlertWindow();
			}
		});
	}

	private  void doNewVersionUpdate(String url) {
		new AsyncUpdateTask().execute(new String[]{url});
	}


	private  void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), UPDATE_SAVENAME)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}


	private class AsyncUpdateTask extends AsyncTask<String, Void, Void> {
		private final ProgressDialog dialog = new ProgressDialog(context);

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
			update();
		}
		@Override
		protected void onPreExecute() {       
			super.onPreExecute();
			dialog.setMessage("最新版本正在下载中...");
			dialog.show();           
		}
		@Override
		protected Void doInBackground(String... params) {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(params[0]);
			HttpResponse response;
			try {
				response = client.execute(get);
				HttpEntity entity = response.getEntity();
				long length = entity.getContentLength();
				InputStream is = entity.getContent();
				FileOutputStream fileOutputStream = null;
				if (is != null) {
					File file = new File(
							Environment.getExternalStorageDirectory(),
							UPDATE_SAVENAME);
					fileOutputStream = new FileOutputStream(file);
					byte[] buf = new byte[1024];
					int ch = -1;
					int count = 0;
					while ((ch = is.read(buf)) != -1) {
						fileOutputStream.write(buf, 0, ch);
						count += ch;
						if (length > 0) {
						}
					}
				}
				fileOutputStream.flush();
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
