package com.caoyanming.curroculum.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.caoyanming.curriculum.R;
import com.caoyanming.curroculum.adapter.CommonAdapter;
import com.caoyanming.curroculum.adapter.ViewHolder;
import com.caoyanming.curroculum.ui.activity.MainActivity;
/**
 * 
 * @author saymagic
 *
 */
public class MenuFragment extends Fragment {

	private LinearLayout layout;
	private ListView list;
	private MainActivity mainActivity;
	List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		layout = (LinearLayout) inflater.inflate(R.layout.menu_layout,
				container, false);
		mainActivity = (MainActivity) getActivity();
		initView();
		return layout;
	}

	private void initView() {
		int[] images = {
				R.drawable.menu_course,
				R.drawable.menu_note,
				R.drawable.menu_setting
				};

		String [] terms = getResources().getStringArray(R.array.left_menu_des);
		for (int i = 0; i <= terms.length-1; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemId", i);
			map.put("itemImg",images[i]);
			map.put("itemText", terms[i]);
			data.add(map);
		}

		list = (ListView) layout.findViewById(R.id.menu_list);
		list.setAdapter(new CommonAdapter<HashMap<String, Object>>(mainActivity, data,R.layout.left_menu_item) {
			@Override
			public void convert(ViewHolder helper, HashMap<String, Object> item) {
				helper.setImageResource(R.id.menuitem_image, Integer.parseInt(item.get("itemImg").toString()));
				helper.setText( R.id.menuitem_text, item.get("itemText").toString());
			}
		});
		//list.setSelector(R.drawable.menu_selector);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(getActivity() instanceof MainActivity) {
					HashMap<String, Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
					int i = (Integer) map.get("itemId");
					BaseFragment frag = getFragment(i);
					((MainActivity) getActivity()).switchContent(frag);
				}
			}
		});
	}
	
	public BaseFragment getFragment(int i) {
		switch (i){
		case 0:
			((MainActivity) getActivity()).setTitle("课程表");
			return new ContentFragment();
		case 1:
			((MainActivity) getActivity()).setTitle("笔记本");
			return new NoteBookFragment();
		default:
			return new ContentFragment();
		}
	}
}
