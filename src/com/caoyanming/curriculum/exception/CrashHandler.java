package com.caoyanming.curriculum.exception;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.caoyanming.curroculum.CurriculumApplication;
import com.caoyanming.curroculum.ui.activity.SendCrashActivity;

/**
 * 
 * @author saymagic
 *
 */
public class CrashHandler implements UncaughtExceptionHandler{

	/** TAG */
	private static final String TAG = "CrashHandler";

	/**
	 *  uploadUrl 
	 *  上传的服务器地址
	 *  
	 * */
	private static final String uploadUrl = "http://3.saymagic.sinaapp.com/ReceiveCrash.php";

	/**
	 * localFileUrl
	 */
	private static String localFileUrl = "";
	/** mDefaultHandler */
	private Thread.UncaughtExceptionHandler defaultHandler;

	/** instance */
	private static CrashHandler instance = new CrashHandler();

	/** infos */
	private Map<String, String> infos = new HashMap<String, String>();

	/** formatter */
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** context*/
	private CurriculumApplication context;
	private CrashHandler() {}

	public static CrashHandler getInstance() {
		if (instance == null) {
			instance = new CrashHandler();
		}
		return instance;
	}

	/**
	 * 
	 * @param ctx
	 */
	public void init(CurriculumApplication ctx) {
		this.context = ctx;
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * uncaughtException
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		handleException(throwable);
		defaultHandler.uncaughtException(thread, throwable);
	}
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		collectDeviceInfo(context);
		writeCrashInfoToFile(ex);
		restart();
		return true;
	}

	/**
	  *
	  * @param ctx
	  * 手机设备相关信息
	  */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
				infos.put("crashTime", formatter.format(new Date()));
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field: fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	  *
	  * @param ex
	  * 将崩溃写入文件系统
	  */
	private void writeCrashInfoToFile(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry: infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);

		  //这里把刚才异常堆栈信息写入SD卡的Log日志里面
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) 
		{
			String sdcardPath = Environment.getExternalStorageDirectory().getPath();
			String filePath = sdcardPath + "/caoyanming/curriculum/crash/";
			localFileUrl = writeLog(sb.toString(), filePath);
		}
	}

	/**
	 * 
	 * @param log
	 * @param name
	 * @return 
	 */
	private String writeLog(String log, String name) 
	{
		CharSequence timestamp = new Date().toString().replace(" ", "");
		timestamp  = "crash";
		String filename = name + timestamp + ".log";

		File file = new File(filename);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		try 
		{
			//			FileOutputStream stream = new FileOutputStream(new File(filename));
			//			OutputStreamWriter output = new OutputStreamWriter(stream);
			file.createNewFile();
			FileWriter fw=new FileWriter(file,true);   
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(log);
			bw.newLine();
			bw.close();
			fw.close();
			return filename;
		} 
		catch (IOException e) 
		{
			Log.e(TAG, "an error occured while writing file...", e);
			e.printStackTrace();
			return null;
		}
	}

	private void restart(){
		 try{    
             Thread.sleep(2000);    
         }catch (InterruptedException e){    
             Log.e(TAG, "error : ", e);    
         }     
         Intent intent = new Intent(context.getApplicationContext(), SendCrashActivity.class);  
         PendingIntent restartIntent = PendingIntent.getActivity(    
        		 context.getApplicationContext(), 0, intent,    
                 Intent.FLAG_ACTIVITY_NEW_TASK);                                                 
         AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);    
         mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,    
                 restartIntent);  
	}
	
}
