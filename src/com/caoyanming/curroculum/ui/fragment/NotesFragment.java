package com.caoyanming.curroculum.ui.fragment;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.caoyanming.curriculum.R;
import com.caoyanming.curroculum.adapter.NoteAdapter;
import com.caoyanming.curroculum.data.bean.Note;
import com.caoyanming.curroculum.data.bean.Notebook;
import com.caoyanming.curroculum.ui.activity.MainActivity;

public class NotesFragment extends BaseFragment {

	private RelativeLayout layout;
	public String EXPANDED = "EXPANDED";
	public ArrayList<Note> itemList;
	public ListView listView;
	public int number;
	private Notebook notebook;
	private NoteAdapter adapter;
	private MainActivity mainActivity;

	public NotesFragment(Notebook notebook){
		this.notebook = notebook;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = (RelativeLayout) inflater.inflate(R.layout.notes_layout, container, false);
		mainActivity = (MainActivity) getActivity();
		this.listView = ((ListView) layout.findViewById(R.id.notes_listview));
		// this.listView.setDivider(getResources().getDrawable(android.R.color.white));
		this.listView.setDivider(null);
		this.listView.setOnItemClickListener(new ItemClick());
		return layout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		showUpdate();
	}


	public void showUpdate() {
		// TODO 自动生成的方法存根
		this.itemList = new ArrayList<Note>();
		if(notebook.getNote() != null ){
			for(Note localNotepad:notebook.getNote()){
				this.itemList.add(localNotepad);
				this.number = this.itemList.size();
			}
		}
		this.adapter = new NoteAdapter(mainActivity, itemList);
		listView.setAdapter(adapter);

	}


	class ItemClick implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> paramAdapterView,
				View paramView, int paramInt, long paramLong) {
			// TODO 自动生成的方法存根
			System.out.println("item----------click");
			Note note =  itemList
					.get(paramInt);
			if (((Boolean) note.isExpanded()).booleanValue()) {
				note.setExpanded(Boolean.valueOf(false));
			} else {
				note.setExpanded(Boolean.valueOf(true));
			}
			adapter.notifyDataSetChanged();
		}

	}
}
