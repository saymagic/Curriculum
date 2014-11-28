package com.caoyanming.curriculum.ui.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.caoyanming.curriculum.R;
import com.caoyanming.curriculum.ui.fragment.BaseFragment;
import com.caoyanming.curriculum.ui.fragment.ContentFragment;
import com.caoyanming.curriculum.ui.fragment.MenuFragment;
import com.slidingmenu.lib.SlidingMenu;
/**
 * 
 * @author saymagic
 *
 */
public class MainActivity extends BaseActivity {

	private SlidingMenu menu;
	private BaseFragment conFragment;
	private MenuFragment menuFragment;
	//private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.activity_main);
		if(null == conFragment)
		{
			conFragment = new ContentFragment();//新建主页
		}
		if(null == menuFragment)
			menuFragment = new MenuFragment();//新建菜单
		menu = new SlidingMenu(this,SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu_left);
		menu.setShadowWidth(20);//设置阴影有立体效果
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffset(50);
		menu.setBehindScrollScale(1);
		menu.setBehindWidth((int) (getWindowManager().getDefaultDisplay().getWidth()*0.6));
		menu.setFadeDegree(1.0f);
		//限制手势，防止和课程表手势冲突
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		menu.setMode(SlidingMenu.LEFT);
		initView();
	}

	private void initView() {
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		ft.replace(R.id.content_frame, conFragment);
		ft.replace(R.id.menu_frame, menuFragment);
		ft.commit();
		
	}

	protected void onTitleLeftButtonClicked(View view) {
		menu.toggle();
	}
	
	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
	}
	//切换主页对应菜单的内容
	public void switchContent(BaseFragment fragment) {
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		conFragment = fragment;
		ft.replace(R.id.content_frame, conFragment);
		ft.commit();
		menu.showContent();
	}
}
