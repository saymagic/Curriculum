package com.caoyanming.curroculum.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.caoyanming.curriculum.R;
import com.caoyanming.curroculum.ui.activity.MainActivity;
/**
 * 
 * @author saymagic
 *
 */
public class MenuFragment extends Fragment {

	private LinearLayout layout;
	private ListView list;
	ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		layout = (LinearLayout) inflater.inflate(R.layout.menu_layout,
				container, false);
		initView();
		return layout;
	}

	private void initView() {

		String [] terms = getResources().getStringArray(R.array.left_menu_des);
		for (int i = 0; i <= terms.length-1; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemId", i);
			map.put("itemText", terms[i]);
			data.add(map);
		}

		list = (ListView) layout.findViewById(R.id.menu_list);

		SimpleAdapter simperAdapter = new SimpleAdapter(getActivity(), data,
				R.layout.left_menu_item, new String[] { "itemImage", "itemText" },
				new int[] { R.id.menuitem_image, R.id.menuitem_text });
		list.setAdapter(simperAdapter);
		list.setSelector(R.drawable.menu_selector);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(getActivity() instanceof MainActivity) {
					
				}
			}
		});
	}
}
