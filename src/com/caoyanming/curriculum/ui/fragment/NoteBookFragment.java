package com.caoyanming.curriculum.ui.fragment;


import java.util.List;

import so.cym.swipemenulistview.SwipeMenu;
import so.cym.swipemenulistview.SwipeMenuCreator;
import so.cym.swipemenulistview.SwipeMenuItem;
import so.cym.swipemenulistview.SwipeMenuListView;
import so.cym.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.caoyanming.curriculum.R;
import com.caoyanming.curriculum.adapter.CommonAdapter;
import com.caoyanming.curriculum.adapter.ViewHolder;
import com.caoyanming.curriculum.data.DataManager;
import com.caoyanming.curriculum.data.bean.Notebook;
import com.caoyanming.curriculum.ui.AlertWindow;
import com.caoyanming.curriculum.ui.UIUtils;
import com.caoyanming.curriculum.ui.activity.EditActivity;
import com.caoyanming.curriculum.ui.activity.MainActivity;
import com.caoyanming.util.T;
import com.caoyanming.util.TimeUtil;
/**
 * 
 * @author saymagic
 *
 */
public class NoteBookFragment extends BaseFragment {

	private LinearLayout layout;
	private SwipeMenuListView notebookListView;
	private MainActivity mainActivity;
	private List<Notebook> notebookList;
	private CommonAdapter notebookAdapter;
	
//	private static NoteBookFragment instance;
//
//	/**
//	 * 单例获取该DataManager
//	 * 
//	 * @param context
//	 * @return
//	 */
//	public static synchronized NoteBookFragment getNoteBookFragment()
//	{
//		if (instance == null)
//		{
//			synchronized (ContentFragment.class)
//			{
//				if (instance == null)
//					instance = new NoteBookFragment();
//			}
//		}
//
//		return instance;
//	}
//	
	
	public NoteBookFragment(){}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = (LinearLayout) inflater.inflate(R.layout.notebook_layout, container, false);
		mainActivity = (MainActivity) getActivity();
		notebookListView = (SwipeMenuListView) layout.findViewById(R.id.notebook_List);
		notebookList =  DataManager.getDataManager(mainActivity).getAllNotebook();
		notebookListView.setAdapter(notebookAdapter = new CommonAdapter<Notebook>(mainActivity,notebookList, R.layout.notebook_list_item) {
			@Override
			public void convert(ViewHolder helper, Notebook item) {
				helper.setText(R.id.notebook_item_title, item.getTitle());  
			}
		});
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem share = new SwipeMenuItem(
						mainActivity);
				// set item background
				share.setBackground(new ColorDrawable(Color.rgb(0xFF,
						0x30, 0x30)));
				// set item width
				share.setWidth(dp2px(90));
				// set a icon
				share.setTitle("删  除");
				share.setTitleSize(18);

				share.setTitleColor(Color.WHITE);

				// add to menu
				menu.addMenuItem(share);
			}
		};

		// set creator
		notebookListView.setMenuCreator(creator);

		notebookListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					break;
				case 1:
					UIUtils.showAlertWindowWithDeleteOnRight(mainActivity, null, "删除后将会删除该笔记本下所有笔记", "是", new AlertWindow.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							UIUtils.dismissAlertWindow();
							Notebook noteBook = notebookList.get(position);
							DataManager.getDataManager(mainActivity).deleteNotebook(noteBook);	
							notebookList.remove(position);
							notebookAdapter.notifyDataSetChanged();
							T.show(mainActivity, "删除成功",1000);
						}
					}, "否", new AlertWindow.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							UIUtils.dismissAlertWindow();
						}
					});

					break;
				}
			}
		});

		notebookListView.setOnItemClickListener(new ItemClick());
		return layout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		mainActivity.setTitle("笔记本");
		super.onResume();
	}
	class ItemClick implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> paramAdapterView,
				View paramView, int paramInt, long paramLong) {
			mainActivity.switchContent(new NotesFragment(notebookList.get(paramInt)));

		}

	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
}
