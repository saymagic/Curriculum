package com.caoyanming.curroculum.ui.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.caoyanming.curriculum.R;
import com.caoyanming.curroculum.data.bean.Notebook;
import com.caoyanming.curroculum.ui.view.DrawLine;
import com.caoyanming.util.T;
import com.caoyanming.util.TimeUtil;

public class EditActivity extends BaseActivity {
	private String content;
	private Context context = this;
	private String date;
	private EditText editText;
	private String id;
	private String notebookid;
	private TextView textView;

	@Override
	protected void onCreate(Bundle paramBundle) {
		// TODO 自动生成的方法存根
		super.onCreate(paramBundle);
		setContentLayout(R.layout.showedit);
		this.textView = ((TextView) findViewById(R.id.editdate));
		this.editText = ((DrawLine) findViewById(R.id.edittexttwo));
		this.date = getIntent().getStringExtra("dateItem");
		this.content = getIntent().getStringExtra("contentItem");
		this.id = getIntent().getStringExtra("idItem");
		this.notebookid= getIntent().getStringExtra("notebookid");
		System.out.println("-----idItem-----id=" + id);
		this.editText.setSelection(this.editText.length());
		this.editText.setText(this.content);
		this.textView.setText(this.date);
		this.date = TimeUtil.getDate();
		this.textView.setText(this.date);
		this.setRightImageRes(R.drawable.img_actionbar_overflow);
		this.setTitle("修改笔记");


	}

	@Override
	protected void onTitleLeftButtonClicked(View view) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(EditActivity.this)   
		.setMessage("您需要保存刚才的修改吗")  
		.setPositiveButton("是", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveInstanceNote(EditActivity.this.notebookid);
			}
		})  
		.setNegativeButton("否", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditActivity.this.finish();
			}
		}).show();
	}

	@Override
	protected void onTitleRightButtonClicked(View view) {
		// TODO Auto-generated method stub
		super.onTitleRightButtonClicked(view);
	}

	private void saveInstanceNote(String notebookid){
		// TODO 自动生成的方法存根
//		SQLiteDatabase localSqLiteDatabase = 
//				new SqliteHelper(EditActivity.this.context, null, null, 0).getWritableDatabase();
//		Notepad localNotepad = new Notepad();
//		ChangeNotepadSqlite localChangeSqlite = new ChangeNotepadSqlite();
//		String strContent = EditActivity.this.editText.getText()
//				.toString();
//		if (strContent.equals("")) {
//			Toast.makeText(EditActivity.this.context, "内容为空",
//					Toast.LENGTH_SHORT).show();
//			return;
//		}
//		String strTitle = strContent.length() > 11 ? " "
//				+ strContent.substring(0, 11) : strContent;
//				localNotepad.setContent(strContent);
//				localNotepad.setTitle(strTitle);
//				localNotepad.setdata(date);
//				localNotepad.setid(id);
//				localNotepad.setNotebookId(notebookid);
//				localChangeSqlite.update(localSqLiteDatabase, localNotepad);
//				T.showLong(getApplication(), "修改成功");
//				finish();
	}

}
