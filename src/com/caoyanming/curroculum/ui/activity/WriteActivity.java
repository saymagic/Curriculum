package com.caoyanming.curroculum.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.caoyanming.curriculum.R;
import com.caoyanming.curroculum.data.DataManager;
import com.caoyanming.curroculum.data.bean.Note;
import com.caoyanming.curroculum.data.bean.Notebook;
import com.caoyanming.curroculum.ui.view.DrawLine;
import com.caoyanming.util.T;
import com.caoyanming.util.TimeUtil;

public class WriteActivity extends BaseActivity {
	private Button cancelButton;
	private Context context = this;
	private String date;
	private EditText editText;
	private Button sureButton;
	private TextView textView;
	private Notebook notebook;


	protected void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentLayout(R.layout.writedown);
		notebook = (Notebook) getIntent().getSerializableExtra("notebook"); 
		this.textView = ((TextView)findViewById(R.id.writedate));
		this.editText = ((DrawLine)findViewById(R.id.edittext));
		this.cancelButton = ((Button)findViewById(R.id.button));
		this.sureButton = ((Button)findViewById(R.id.button2));
		this.date = TimeUtil.getDate();
		this.textView.setText(this.date);
		this.setRightImageRes(R.drawable.img_actionbar_overflow);
		this.setTitle("新建笔记");
	}

	@Override
	protected void onTitleLeftButtonClicked(final View view) {
		new AlertDialog.Builder(WriteActivity.this)   
		.setMessage("您需要保存刚才的笔记吗")  
		.setPositiveButton("是", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Note note = new Note();
				note.setNotebook(notebook);
				note.setContent(editText.getText().toString());
				note.setDate(TimeUtil.getDate());
				DataManager.getDataManager(context).addNote(note);
				T.showLong(context, "Die");
			}
		})  
		.setNegativeButton("否", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				WriteActivity.this.finish();
			}
		}).show();
	}



}


























