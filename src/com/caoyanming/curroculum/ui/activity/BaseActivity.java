package com.caoyanming.curroculum.ui.activity;



import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caoyanming.curroculum.R;
import com.caoyanming.util.L;



/**
 * 
 * @author saymagic
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BaseActivity extends Activity {

	private static final int ACTIVITY_RESUME = 0;
	private static final int ACTIVITY_STOP = 1;
	private static final int ACTIVITY_PAUSE = 2;
	private static final int ACTIVITY_DESTROY = 3;

	protected int activityState;

	private LinearLayout ly_content;  
	// 内容区域的布局  
	private View contentView;  

	private ImageView leftButton, rightButton;
	private TextView baseTitle;
	private View titleView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L.d("onCreate");
		setContentView(R.layout.activity_base);
		setTitle("");
		findAndRegisterView();
	}

	private void findAndRegisterView(){

		ly_content = (LinearLayout) findViewById(R.id.ly_base_content);  
		titleView = findViewById(R.id.titleView);
		
		leftButton = (ImageView) findViewById(R.id.base_left_button);
		rightButton = (ImageView) findViewById(R.id.base_right_button);
		baseTitle = (TextView) findViewById(R.id.base_title);

		leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onTitleLeftButtonClicked(view);
			}
		});

		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onTitleRightButtonClicked(v);
			}
		});

	}

	/**
	 * 该函数属于回调函数，用于子类来继承来实现在子类中title左侧按钮的点击事件。
	 * @param view
	 */
	protected void onTitleLeftButtonClicked(View view) 
	{
		this.finish();
	}


	/**
	 * 该函数属于回调函数，用于子类来继承来实现在子类中title左侧按钮的点击事件。
	 * @param view
	 */
	protected void onTitleRightButtonClicked(View view) {}

	/*** 
	 * 设置内容区域 
	 *  
	 * @param resId 资源文件ID 
	 */  
	@SuppressWarnings("deprecation")
	protected void setContentLayout(int resId) {  
		L.d("setContentLayout");
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		contentView = inflater.inflate(resId, null);  
		@SuppressWarnings("deprecation")
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,  
				LayoutParams.FILL_PARENT);  
		contentView.setLayoutParams(layoutParams);  
		contentView.setBackgroundDrawable(null);  
		if (null != ly_content) {  
			ly_content.addView(contentView);  
		}  

	}  

	/*** 
	 * 设置内容区域 
	 *  
	 * @param view 
	 *            View对象 
	 */  
	protected void setContentLayout(View view) {  
		if (null != ly_content) {  
			this.contentView = view;
			ly_content.addView(view);  
		}  
	} 

	/**
	 * 获得内容的view
	 * 
	 * @return
	 */
	protected View getContentView() {
		return contentView;
	}

	/** 
	 * 得到左边的按钮 
	 *  
	 * @return 
	 */ 
	public ImageView getLeftImageView() {
		return leftButton;
	}

	/** 
	 * 得到右边的按钮 
	 *  
	 * @return 
	 */  
	public ImageView getRightImageView() {  
		return rightButton;  
	} 

	/** 
	 * 设置标题 
	 *  
	 * @param title 
	 */  
	public void setTitle(String title) {  

		if (null != baseTitle) {  
			baseTitle.setText(title);  
		}  

	}  
	
	
	public String getTitleString(){
		return baseTitle.getText().toString();
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		L.d(this.getClass()+"---------onStart ");
	}

	/** 
     * 设置左边按钮的图片资源 
     *  
     * @param resId 
     */  
	protected void setLeftImageRes(int resId) {  
        if (null != leftButton) {  
        	leftButton.setBackgroundResource(resId);  
        }  
    }  
    
    /** 
     * 设置左边按钮的图片资源 
     *  
     * @param bm 
     */  
    @SuppressWarnings("deprecation")
	protected void setLeftImageRes(Drawable drawable) {  
        if (null != leftButton) {  
        	leftButton.setBackgroundDrawable(drawable);  
        }  
  
    } 

	/** 
     * 设置右边按钮的图片资源 
     *  
     * @param resId 
     */  
    public void setRightImageRes(int resId) {  
        if (null != rightButton) {  
        	rightButton.setBackgroundResource(resId);  
        }  
    } 

    /** 
     * 设置右边按钮的图片资源 
     *  
     * @param bm 
     */  
    @SuppressWarnings("deprecation")
    public void setRightImageRes(Drawable drawable) {  
        if (null != rightButton) {  
        	rightButton.setBackgroundDrawable(drawable);  
        }  
  
    } 
    
    
    /** 
     * 隐藏上方的标题栏 
     */  
    public void hideTitleView() {  
  
        if (null != titleView) {  
            titleView.setVisibility(View.GONE);  
        }  
    }  
    
    /** 
     * 隐藏左边的按钮 
     */  
    public void hideLeftButton() {  
  
        if (null != leftButton) {  
        	leftButton.setVisibility(View.GONE);  
        }  
  
    }  
  
    /*** 
     * 隐藏右边的按钮 
     */  
    protected  void hideRightButton() {  
        if (null != rightButton) {  
        	rightButton.setVisibility(View.GONE);  
        }  
  
    }  
    
    @Override
	public void startActivityForResult(Intent intent, int requestCode) { 
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.slide_in_right, R.anim.scale_small);
	}
	
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.scale_small);
	}
	
	@Override
	public void onBackPressed() {
		overridePendingTransition(R.anim.scale_big, R.anim.slide_out_right);
		this.finish();
	}
    
	@Override
	protected void onResume() {
		super.onResume();
		activityState = ACTIVITY_RESUME;
		L.d(this.getClass() + "---------onResume ");
	}

	@Override
	protected void onStop() {
		super.onResume();
		activityState = ACTIVITY_STOP;
		L.d(this.getClass() + "---------onStop ");
	}

	@Override
	protected void onPause() {
		super.onPause();
		activityState = ACTIVITY_PAUSE;
		L.d(this.getClass() + "---------onPause ");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		L.d(this.getClass() + "---------onRestart ");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		activityState = ACTIVITY_DESTROY;
		L.d(this.getClass() + "---------onDestroy ");
	}

}
