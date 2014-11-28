package com.caoyanming.curriculum.ui;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.caoyanming.curriculum.R;
/**
 * 
 * @author saymagic
 *
 */
public class AlertWindow extends Dialog {

	/** mStyle */
	private int mStyle = 0x00000007;

	/** HAS_TITLE */
	public static final int HAS_TITLE = 0x000000001;
	/** HAS_OK */
	public static final int HAS_OK = 0x00000002;
	/** HAS_CANCEL */
	public static final int HAS_CANCEL = 0x00000004;
	/** HAS_LIST */
	public static final int HAS_LIST = 0x00000008;
	/** HAS_MENU */
	public static final int HAS_MENU = 0x00000010;
	/** HAS_CUSTOM */
	public static final int HAS_CUSTOM = 0x00000020;
	/** HAS_DELETEONLEFT */
	public static final int HAS_DELETEONLEFT = 0x00000040;
	/** HAS_DELETEONRIGHT */
	public static final int HAS_DELETEONRIGHT = 0x00000080;
	/** HAS_TIMEPICKER**/
	public static final int HAS_TIMEPICKER = 0x00000100;
	/** MAX_HEIGHT_WITH_LIST_IN_DP */
	public static final int MAX_HEIGHT_WITH_LIST_IN_DP = 302;

	/** mLLTitle */
	private ViewGroup mLLTitle;
	/** mContent */
	private LinearLayout mLLContent;
	/** mTvTitle */
	private TextView mTvTitle;
	/** mTvContent */
	private TextView mTvContent;
	/** mIvDivider */
	private ImageView mIvDivider;
	/** mBtnCancel */
	private Button mBtnCancel;
	/** mBtnOK */
	private Button mBtnOK;
	/**mTimePicker*/
	private TimePicker mTimePicker;
	/** mTitle */
	private CharSequence mTitle;
	/** mMsg */
	private CharSequence mMsg;
	/** mAdpater */
	private ListAdapter mAdpater;

	/** mLstContent */
	private ListView mLstContent;

	/** mPositiveListener */
	private OnClickListener mPositiveListener;
	/** mNegativeListener */
	private OnClickListener mNegativeListener;
	/** mItemListener */
	private OnListItemClickListener mItemListener; 

	/** mPostiveButton */
	private CharSequence mPostiveButton;
	/** mNegativeButton */
	private CharSequence mNegativeButton;

	/** mLstMenu */
	private List<String> mLstMenu;

	/** mInflater */
	private LayoutInflater mInflater;

	/** mCustomView */
	private View mCustomView;

	/** mRootView */
	private View mRootView;

	/** mClosable */
	private boolean mClosable = true;

	/** mMaxWidth */
	private int mMaxWidth;

	/** mContentView */
	private View mContentView;

	private Context context;
	public AlertWindow(Context context) {
		super(context);
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public AlertWindow(Context context, int style) {
		super(context, R.style.dialog);
		this.context = context;

		init(context, style);
	}

	public AlertWindow(Context context, int style, List<String> lstMenu) {
		super(context, R.style.dialog);

		init(context, style);
		mLstMenu = lstMenu;
		this.context = context;

	}

	public AlertWindow(Context context, int style, int theme, List<String> lstMenu) {
		super(context, theme);
		init(context, style);
		mLstMenu = lstMenu;
		this.context = context;

	}

	public AlertWindow(Context context, int style, View customView) {
		super(context, R.style.dialog);
		init(context, style);
		mCustomView = customView;
		this.context = context;

	}

	private void init(Context context, int style) {
		mStyle = style;
		mMaxWidth = context.getResources().getDimensionPixelSize(
				R.dimen.alertwindow_max_width);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		mInflater = (LayoutInflater) getContext()
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		getWindow().setBackgroundDrawable(new BitmapDrawable());

		ViewGroup.LayoutParams rootParams = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		LinearLayout rootLayout = new LinearLayout(getContext());
		rootLayout.setOrientation(LinearLayout.VERTICAL);
		rootLayout.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.popup_bg));
		mRootView = rootLayout;
		setContentView(rootLayout, rootParams);
		if ((mStyle & HAS_MENU) != 0) {
			LinearLayout ll = new LinearLayout(getContext());
			//ll.setBackgroundResource(0);
			ll.setOrientation(LinearLayout.VERTICAL);
			//ll.setPadding(0, UIUtils.dip2px(4), 0, UIUtils.dip2px(4));
			for (int i = 0; i < mLstMenu.size(); i++) {

				View view = mInflater.inflate(R.layout.alert_window_menu_item, null);
				ImageView iv = (ImageView) view.findViewById(R.id.divider);
				final Button btn = (Button) view.findViewById(R.id.btn);
				if (i == 0) {
					iv.setVisibility(View.GONE);
				}

				btn.setText(mLstMenu.get(i));
				final int index = i;
				btn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mItemListener != null) {
							mItemListener.onItemClick(btn, index);
						}
					}
				});
				ll.addView(view);
			}
			rootLayout.addView(ll);
			mContentView = ll;
		} else if ((mStyle & HAS_LIST) != 0) {
			mContentView = mInflater.inflate(R.layout.alert_window_list, null);
			rootLayout.addView(mContentView);

			mLLTitle = (ViewGroup) findViewById(R.id.alert_dialog_title);
			mTvTitle = (TextView) findViewById(R.id.alert_dialog_title_content);
			mIvDivider = (ImageView) findViewById(R.id.alert_dialog_title_divider);
			mLstContent = (ListView) findViewById(R.id.alert_dialog_listview);
			mBtnCancel = (Button) findViewById(R.id.alert_dialog_btnCancel);
			mBtnOK = (Button) findViewById(R.id.alert_dialog_btnOK);
			ImageView divider = (ImageView) findViewById(R.id.iv_divider);
			if ((mStyle & HAS_OK) == 0) {
				mBtnOK.setVisibility(View.GONE);
				divider.setVisibility(View.GONE);
			}
			mLstContent.setAdapter(mAdpater);
			if (Build.VERSION.SDK_INT >= 9) {
				mLstContent.setOverScrollMode(View.OVER_SCROLL_NEVER);
			}
			
			//mLstContent.setDivider(getContext()
					//    .getResources().getDrawable(R.drawable.bg_alert_window_list_divider));
			//mLstContent.setDividerHeight(1);

			mLstContent.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					if (mItemListener != null) {
						mItemListener.onItemClick(arg1, arg2);
					}
				}
			});
		}  else if ((mStyle & HAS_TIMEPICKER) != 0) {
			mContentView = mInflater.inflate(R.layout.alert_window_timepicker, null);
			rootLayout.addView(mContentView);
			mLLTitle = (ViewGroup) findViewById(R.id.alert_dialog_title);
			mTvTitle = (TextView) findViewById(R.id.alert_dialog_title_content);
			mIvDivider = (ImageView) findViewById(R.id.alert_dialog_title_divider);
			mBtnCancel = (Button) findViewById(R.id.alert_dialog_btnCancel);
			mBtnOK = (Button) findViewById(R.id.alert_dialog_btnOK);
			mTimePicker = (TimePicker) findViewById(R.id.alert_dialog_timepickerview);
			ImageView divider = (ImageView) findViewById(R.id.iv_divider);
			if ((mStyle & HAS_OK) == 0) {
				mBtnOK.setVisibility(View.GONE);
				divider.setVisibility(View.GONE);
			}
			
		} else if ((mStyle & HAS_CUSTOM) != 0) {
			if (mCustomView != null) {
				rootLayout.addView(mCustomView);
			}
		} else {
			mContentView = mInflater.inflate(R.layout.alert_window, null);
			rootLayout.addView(mContentView);

			mLLTitle = (ViewGroup) findViewById(R.id.alert_dialog_title);
			mLLContent = (LinearLayout) findViewById(R.id.alert_dialog_content);
			mTvTitle = (TextView) findViewById(R.id.alert_dialog_title_content);
			mTvContent = (TextView) findViewById(R.id.alert_dialog_content_msg);
			mIvDivider = (ImageView) findViewById(R.id.alert_dialog_title_divider);
			mBtnCancel = (Button) findViewById(R.id.alert_dialog_btnCancel);
			mBtnOK = (Button) findViewById(R.id.alert_dialog_btnOK);
			ImageView okCancelDiliver = (ImageView) findViewById(R.id.ok_cancel_diliver);
			if ((mStyle & HAS_CANCEL) == 0) {
				mBtnCancel.setVisibility(View.GONE);
			} else {
				okCancelDiliver.setVisibility(View.VISIBLE);
			}

			if (mPostiveButton != null && !mPostiveButton.equals("")) {
				mBtnOK.setText(mPostiveButton);
			}

			if (mNegativeButton != null  && !mNegativeButton.equals("")) {
				mBtnCancel.setText(mNegativeButton);
			}

			mTvContent.setText(mMsg);
		} 
		if ((mStyle & HAS_TITLE) == 0) {
			if (mLLTitle != null) {
				mLLTitle.setVisibility(View.GONE);
			}
			if (mIvDivider != null) {
				mIvDivider.setVisibility(View.GONE);
			}
		} else {
			if (mTvTitle != null) {
				mTvTitle.setText(mTitle);
			}
		}

		//        if (mIvDivider != null) {
		//            ColorDrawable cd = new ColorDrawable(Color.rgb(76, 106, 141));
		//            cd.setBounds(0, 0, 1, 2);
		//            mIvDivider.setImageDrawable(cd);
		//        }

		if (mBtnCancel != null) {
			mBtnCancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mNegativeListener != null) {
						mNegativeListener.onClick(AlertWindow.this,
								BUTTON_NEGATIVE);
					}  
				}
			});
			if ((mStyle & HAS_DELETEONLEFT) != 0) {
				ColorStateList csl = (ColorStateList) getContext().getResources().getColorStateList(R.color.dialog_text_delete);
				mBtnCancel.setTextColor(csl);
				mBtnCancel.setBackgroundResource(R.drawable.bg_dialog_btn_delete);
			}
		}

		if (mBtnOK != null) {
			mBtnOK.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mPositiveListener != null) {
						mPositiveListener.onClick(AlertWindow.this,
								BUTTON_POSITIVE);
					}
				}
			});
			if ((mStyle & HAS_DELETEONRIGHT) != 0) {
				ColorStateList csl = (ColorStateList) getContext().getResources().getColorStateList(R.color.dialog_text_delete);
				mBtnOK.setTextColor(csl);
				mBtnOK.setBackgroundResource(R.drawable.bg_dialog_btn_delete);
			}
		}

		if (mClosable) {
			setCancelable(true);
			setCanceledOnTouchOutside(true);
		} else {
			setCancelable(false);
			setCanceledOnTouchOutside(false);
		}

		if ((mStyle & HAS_TITLE) == 0) {
			if (mTvContent != null) {
				mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
				mTvContent.setTextColor(Color.BLACK);
			}
		}

		onOrientationChanged();
	}

	public void setClosable(boolean closable) {
		mClosable = closable;
	}

	private void checkViewSize(View view, DisplayMetrics dm) {
		int width = dm.widthPixels * 11 / 12;
		int height = ViewGroup.LayoutParams.WRAP_CONTENT;
		if (mLstContent != null) {
			int total = UIUtils.getTotalHeightofListView(mLstContent);
			int maxListHeight = UIUtils.dip2px(context,MAX_HEIGHT_WITH_LIST_IN_DP);
			if (total > maxListHeight) {
				ViewGroup.LayoutParams params = mLstContent.getLayoutParams();
				if (null != params) {
					// 额外留出20dip，防止在横屏状下显示不正确
					// TODO: use get height to replace 123dip.
					// 123 = 53(title) + 2(deliver) + 48(bottom) + 20(paddingTop + paddingBottom)
					int disHeight = dm.heightPixels * 15 / 16 - UIUtils.dip2px(context,123);
					if (disHeight < maxListHeight) {
						params.height = disHeight;
					} else {
						params.height = maxListHeight;
					}
				}
			}
		}
		width = width > mMaxWidth ? mMaxWidth : width;
		ViewGroup.LayoutParams params = view.getLayoutParams();
		if (params != null) {
			params.width = width;
			params.height = height;
		} else {
			view.setLayoutParams(new ViewGroup.LayoutParams(width, height));
		}
	}

	public void onOrientationChanged() {

		//        if (UIUtils.isTablet(getContext())) {
		//            return;
		//        }

		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) 
				getContext().getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);

		//        if (mLLContent != null && mLLTitle != null) {
		//            checkViewSize(mLLTitle, dm);
		//            checkViewSize(mLLContent, dm);
		//        } else if (mLstContent != null) {
		//            checkViewSize(mLstContent, dm);
		//        } else if (mCustomView != null) {
		//            checkViewSize(mCustomView, dm);
		//        }
		checkViewSize(mRootView, dm);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
	}

	public void setMessage(CharSequence msg) {
		mMsg = msg;
	}

	public void setListAdapter(ListAdapter adapter) {
		mAdpater = adapter;
	}

	public void setButtonListener(
			OnClickListener positiveListener,
			OnClickListener negativeListener) {
		mPositiveListener = positiveListener;
		mNegativeListener = negativeListener;
	}

	public void setButtonText(CharSequence positive, CharSequence negative) {
		mPostiveButton = positive;
		mNegativeButton = negative;
	}

	public void setPositiveButton(CharSequence text, OnClickListener listener) {
		mPostiveButton = text;
		mPositiveListener = listener;
	}

	public void setNegativeButton(CharSequence text, OnClickListener listener) {
		mNegativeButton = text;
		mNegativeListener = listener;
	}

	public void setItemClickListener(OnListItemClickListener lisetner) {
		mItemListener = lisetner;
	}

	public interface OnClickListener {
		public void onClick(DialogInterface dialog, int which);
	}

	public interface OnListItemClickListener {
		public void onItemClick(View view, int which);
	}
}