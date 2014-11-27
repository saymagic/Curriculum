package com.caoyanming.curroculum.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.caoyanming.curriculum.R;
import com.caoyanming.curroculum.data.DataManager;
import com.caoyanming.curroculum.data.bean.Note;
import com.caoyanming.curroculum.ui.AlertWindow;
import com.caoyanming.curroculum.ui.UIUtils;
import com.caoyanming.curroculum.ui.activity.EditActivity;
import com.caoyanming.curroculum.ui.activity.MainActivity;
import com.caoyanming.curroculum.ui.view.TextViewLine;
import com.caoyanming.util.T;

public class NoteAdapter extends BaseAdapter {

	public Context context;
	public Context activity;
	public LayoutInflater inflater;
	public List<Note> list;

	public NoteAdapter(Activity activity, List<Note> list) {

		this.context = activity;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		// 此处在去掉if语句之后，刷新语句重新生效
		SetShow setShow = new SetShow();
		// 取出map中的展开标记
		Note note = list.get(arg0);
		boolean boo = (Boolean) note.isExpanded();
		if (!boo) {
			arg1 = inflater.inflate(R.layout.showtypes, arg2, false);
			setShow.contentView = (TextView) arg1
					.findViewById(R.id.contentTextView);
			setShow.dateView = (TextView) arg1.findViewById(R.id.dateTextView);
			String str = (String) list.get(arg0).getTitle();
			String dateStr = (String) list.get(arg0).getDate();
			setShow.contentView.setText("   " + str);
			setShow.dateView.setText(dateStr);
			setShow.showButtonWrite = (Button) arg1
					.findViewById(R.id.smallbutton1);
			setShow.showButtonDelete = (Button) arg1
					.findViewById(R.id.smallbutton2);
			setShow.showButtonWrite.setOnClickListener(new WriteButtonListener(
					arg0));
			setShow.showButtonDelete
					.setOnClickListener(new DeleteButtonListener(arg0));
		} else {
			arg1 = inflater.inflate(R.layout.style, arg2, false);
			setShow.cContentView = (TextViewLine) arg1
					.findViewById(R.id.changecontentview);
			setShow.cDateView = (TextView) arg1
					.findViewById(R.id.changedateview);
			String str = (String) list.get(arg0).getContent();
			String dateStr = (String) list.get(arg0).getDate();
			setShow.cContentView.setText("" + str);
			setShow.cDateView.setText(dateStr);
			setShow.styleButtonWrite = (Button) arg1
					.findViewById(R.id.stylebutton1);
			setShow.styleButtonWrite
					.setOnClickListener(new WriteButtonListener(arg0));
			setShow.styleButtonDelete = (Button) arg1
					.findViewById(R.id.stylebutton2);
			setShow.styleButtonDelete
					.setOnClickListener(new DeleteButtonListener(arg0));
		}
		return arg1;
	}

	class WriteButtonListener implements OnClickListener {
		private int position;

		public WriteButtonListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent((MainActivity) context,EditActivity.class);
			Note note = list.get(position);
			Bundle mBundle = new Bundle();   
			mBundle.putSerializable("note", note);   
			intent.putExtras(mBundle);
			((MainActivity) context).startActivity(intent);
		}

	}

	class DeleteButtonListener implements OnClickListener {
		private int position;

		public DeleteButtonListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			UIUtils.showAlertWindowWithDeleteOnRight(context, null, "确定删除", "是", new AlertWindow.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					UIUtils.dismissAlertWindow();
					DataManager.getDataManager(context).deleteNote(list.get(position));
					list.remove(position);
					NoteAdapter.this.notifyDataSetChanged();	
					T.show(context, "删除成功",1000);
				}
			}, "否", new AlertWindow.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					UIUtils.dismissAlertWindow();
				}
			});
		}

	}

	class SetShow {
		public TextView contentView;
		public TextView dateView;
		public TextViewLine cContentView;
		public TextView cDateView;
		public Button styleButtonWrite;
		public Button styleButtonDelete;
		public Button showButtonWrite;
		public Button showButtonDelete;
	}

}
