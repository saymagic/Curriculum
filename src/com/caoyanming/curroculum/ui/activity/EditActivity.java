package com.caoyanming.curroculum.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.caoyanming.curriculum.R;
import com.caoyanming.curroculum.data.DataManager;
import com.caoyanming.curroculum.data.bean.Note;
import com.caoyanming.curroculum.ui.view.DrawLine;
import com.caoyanming.util.TimeUtil;
/**
 * 
 * @author saymagic
 *
 */
public class EditActivity extends BaseActivity {
	private Context context = this;
	private EditText editText;
	private TextView textView;
	private Note note;
	private String text;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentLayout(R.layout.showedit);
		this.note = (Note) getIntent().getSerializableExtra("note");
		this.textView = ((TextView) findViewById(R.id.editdate));
		this.editText = ((DrawLine) findViewById(R.id.edittexttwo));
		this.editText.setSelection(this.editText.length());
		this.editText.setText(this.note.getContent());
		this.textView.setText(TimeUtil.getDate());
		this.setTitle("修改笔记");
		this.text = note.getContent() == null ? "" : note.getContent();
	}

	@Override
	protected void onTitleLeftButtonClicked(View view) {
		final String strContent = editText.getText().toString();

		if(!this.text.equals(strContent)){
			new AlertDialog.Builder(EditActivity.this)   
			.setMessage("您需要保存刚才的修改吗")  
			.setPositiveButton("是", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					note.setContent(strContent);
					String strTitle=strContent.length()>11?" "+strContent.substring(0, 11):strContent;
					note.setTitle(strTitle);
				}
			})  
			.setNegativeButton("否", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			}).show();
		}
		
		note.setDate(TimeUtil.getDate());
		DataManager.getDataManager(context).updateNote(note);
		this.finish();
	}

	@Override
	protected void onTitleRightButtonClicked(View view) {
		super.onTitleRightButtonClicked(view);
	}

}
