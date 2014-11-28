package com.caoyanming.curriculum.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.caoyanming.curriculum.R;
import com.caoyanming.curriculum.data.DataManager;
import com.caoyanming.curriculum.data.bean.Note;
import com.caoyanming.curriculum.data.bean.Notebook;
import com.caoyanming.curriculum.ui.AlertWindow;
import com.caoyanming.curriculum.ui.UIUtils;
import com.caoyanming.curriculum.ui.view.DrawLine;
import com.caoyanming.util.T;
import com.caoyanming.util.TimeUtil;
/**
 * 
 * @author saymagic
 *
 */
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
		this.setTitle("新建笔记");
	}

	@Override
	protected void onTitleLeftButtonClicked(final View view) {
		final String strContent = editText.getText().toString();
		if (TextUtils.isEmpty(strContent)) {
			WriteActivity.this.finish();
		}else{
			UIUtils.showAlertWindowWithDeleteOnRight(WriteActivity.this, null, "您需要保存刚才的笔记吗", "是", new AlertWindow.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Note note = new Note();
					String strTitle=strContent.length()>11?" "+strContent.substring(0, 11):strContent;
					note.setNotebook(notebook);
					note.setContent(strContent);
					note.setTitle(strTitle);
					note.setDate(TimeUtil.getDate());
					DataManager.getDataManager(context).addNote(note);
					T.show(context, "保存成功",1000);
					WriteActivity.this.finish();		
				}
			}, "否", new AlertWindow.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					UIUtils.dismissAlertWindow();
					WriteActivity.this.finish();
				}
			});

			
		}
	}



}


























