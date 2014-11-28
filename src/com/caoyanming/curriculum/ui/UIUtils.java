package com.caoyanming.curriculum.ui;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TimePicker;

import com.caoyanming.curriculum.R;
/**
 * 
 * @author saymagic
 *
 */
public class UIUtils {

	/** mAlertWindow */
	private static AlertWindow mAlertWindow;

	private static AlertWindow getAlertWindow(Context context, int style,
			CharSequence title, CharSequence message, CharSequence positiveText,
			final AlertWindow.OnClickListener positiveListener,
			CharSequence negativeText,
			final AlertWindow.OnClickListener negativeListener) {

		AlertWindow aw = new AlertWindow(context, style);
		mAlertWindow = aw;
		aw.setTitle(title);
		aw.setMessage(message);

		if (positiveText != null) {
			aw.setPositiveButton(positiveText,
					new AlertWindow.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (positiveListener != null) {
						positiveListener.onClick(dialog, which);
					} else {
						dialog.dismiss();
					}
				}
			});
		}
		if (negativeText != null) {
			aw.setNegativeButton(negativeText,
					new AlertWindow.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (negativeListener != null) {
						negativeListener.onClick(dialog, which);
					} else {
						dialog.dismiss();
					}
				}
			});
		}
		aw.show();

		return aw;
	}

	public static AlertWindow showAlertWindow(Context context,
			CharSequence title, CharSequence message, CharSequence positiveText,
			final AlertWindow.OnClickListener positiveListener,
			CharSequence negativeText,
			final AlertWindow.OnClickListener negativeListener) {

		int style = 0;
		if (title != null) {
			style |= AlertWindow.HAS_TITLE;
		}
		if (positiveText != null) {
			style |= AlertWindow.HAS_OK;
		}
		if (negativeText != null) {
			style |= AlertWindow.HAS_CANCEL;
		}
		return getAlertWindow(context, style, title,
				message, positiveText, positiveListener,
				negativeText, negativeListener);
	}

	public static AlertWindow showAlertWindowWithDeleteOnLeft(Context context,
			CharSequence title, CharSequence message, CharSequence positiveText,
			final AlertWindow.OnClickListener positiveListener,
			CharSequence negativeText,
			final AlertWindow.OnClickListener negativeListener) {

		int style = 0;
		if (title != null) {
			style |= AlertWindow.HAS_TITLE;
		}
		if (positiveText != null) {
			style |= AlertWindow.HAS_OK;
		}
		if (negativeText != null) {
			style |= AlertWindow.HAS_CANCEL;
		}
		style |= AlertWindow.HAS_DELETEONLEFT;
		return getAlertWindow(context, style, title,
				message, positiveText, positiveListener,
				negativeText, negativeListener);
	}

	public static AlertWindow showAlertWindowWithDeleteOnRight(Context context,
			CharSequence title, CharSequence message, CharSequence positiveText,
			final AlertWindow.OnClickListener positiveListener,
			CharSequence negativeText,
			final AlertWindow.OnClickListener negativeListener) {

		int style = 0;
		if (title != null) {
			style |= AlertWindow.HAS_TITLE;
		}
		if (positiveText != null) {
			style |= AlertWindow.HAS_OK;
		}
		if (negativeText != null) {
			style |= AlertWindow.HAS_CANCEL;
		}
		style |= AlertWindow.HAS_DELETEONRIGHT;
		return getAlertWindow(context, style, title,
				message, positiveText, positiveListener,
				negativeText, negativeListener);
	}

	public static void showAlertWindowWithOKCancel(Context context,
			boolean hasTitle, String title, String message,
			AlertWindow.OnClickListener positiveListener,
			AlertWindow.OnClickListener negativeListener) {
		int style = 0;
		if (hasTitle) {
			style = AlertWindow.HAS_TITLE | AlertWindow.HAS_CANCEL
					| AlertWindow.HAS_OK;
		} else {
			style = AlertWindow.HAS_CANCEL | AlertWindow.HAS_OK;
		}
		AlertWindow aw = new AlertWindow(context, style);
		mAlertWindow = aw;
		aw.setTitle(title);
		aw.setMessage(message);
		aw.setButtonListener(positiveListener, negativeListener);
		aw.show();
	}

	public static void showModalAlertWindowWithOK(Context context, String msg) {
		int style = AlertWindow.HAS_OK;
		AlertWindow aw = new AlertWindow(context, style);
		mAlertWindow = aw;
		aw.setMessage(msg);
		aw.setButtonListener(new AlertWindow.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}, null);
		aw.setCancelable(false);
		aw.setCanceledOnTouchOutside(false);
		aw.setClosable(false);
		aw.show();
	}


	public static void showAlertWindowWithOK(Context context, boolean hasTitle,
			String title, String message,
			AlertWindow.OnClickListener positiveListener) {
		int style = 0;
		if (hasTitle) {
			style = AlertWindow.HAS_TITLE | AlertWindow.HAS_OK;
		} else {
			style = AlertWindow.HAS_OK;
		}
		AlertWindow aw = new AlertWindow(context, style);
		mAlertWindow = aw;
		aw.setTitle(title);
		aw.setMessage(message);
		aw.setButtonListener(positiveListener, null);
		aw.show();
	}

	public static void showAlertWindowWithCustomButton(Context context,
			boolean hasTitle, String title, String message, String positive,
			String negative, AlertWindow.OnClickListener positiveListener,
			AlertWindow.OnClickListener negativeListener) {
		int style = 0;
		if (hasTitle) {
			style = AlertWindow.HAS_TITLE | AlertWindow.HAS_CANCEL
					| AlertWindow.HAS_OK;
		} else {
			style = AlertWindow.HAS_CANCEL | AlertWindow.HAS_OK;
		}
		AlertWindow aw = new AlertWindow(context, style);
		mAlertWindow = aw;
		aw.setTitle(title);
		aw.setMessage(message);
		aw.setButtonText(positive, negative);
		aw.setButtonListener(positiveListener, negativeListener);
		aw.show();
	}

	public static void showAlertWindowWithOKNoCancelOutside(Context context,
			boolean hasTitle, String title, String message,
			AlertWindow.OnClickListener positiveListener) {
		int style = 0;
		if (hasTitle) {
			style = AlertWindow.HAS_TITLE | AlertWindow.HAS_OK;
		} else {
			style = AlertWindow.HAS_OK;
		}
		AlertWindow aw = new AlertWindow(context, style);
		mAlertWindow = aw;
		aw.setTitle(title);
		aw.setMessage(message);
		aw.setButtonListener(positiveListener, null);
		aw.setCanceledOnTouchOutside(false);
		aw.show();
	}

	public static void showAlertWindowWithList(Context context, String title,
			ListAdapter adapter, AlertWindow.OnListItemClickListener listener) {
		int style = AlertWindow.HAS_LIST;
		if (title != null) {
			style |= AlertWindow.HAS_TITLE;
		}
		AlertWindow aw = new AlertWindow(context, style);
		mAlertWindow = aw;
		aw.setTitle(title);
		aw.setListAdapter(adapter);
		aw.setItemClickListener(listener);
		aw.setButtonListener(null, new AlertWindow.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		aw.show();
	}

	public static void showTimePicker(final Context context,final TimePickerListener timePickerListener){
		View view = LayoutInflater.from(context).inflate(R.layout.alert_window_timepicker, null);
		Button mBtnCancel = (Button) view.findViewById(R.id.alert_dialog_btnCancel);
		Button mBtnOK = (Button) view.findViewById(R.id.alert_dialog_btnOK);
		final TimePicker mTimePicker = (TimePicker) view.findViewById(R.id.alert_dialog_timepickerview);
		mTimePicker.setDrawingCacheBackgroundColor(R.color.white);
		mTimePicker.setIs24HourView(true);
		mTimePicker.setVerticalFadingEdgeEnabled(true);
		UIUtils.showAlertWindowWithCustomView(context, view, true, false);
		mBtnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dismissAlertWindow();
			}
		});
		mBtnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismissAlertWindow();
				//				Calendar c = Calendar.getInstance();
				//				c.set(2000, 2, 1,  mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute(), 0);
				timePickerListener.timeChecked(mTimePicker.getCurrentHour(),mTimePicker.getCurrentMinute());
			}
		});
	}
	public static void showAlertWindowWithListOK(Context context, String title,
			ListAdapter adapter, AlertWindow.OnClickListener positiveListener,
			AlertWindow.OnClickListener negativeListener) {
		int style = AlertWindow.HAS_LIST;
		if (title != null) {
			style |= AlertWindow.HAS_TITLE;
			style |= AlertWindow.HAS_OK;
		}
		AlertWindow aw = new AlertWindow(context, style);
		mAlertWindow = aw;
		aw.setTitle(title);
		aw.setListAdapter(adapter);
		aw.setButtonListener(positiveListener, negativeListener);
		aw.show();
	}

	public static void showAlertWindowWithMenu(Context context,
			List<String> lstStr, AlertWindow.OnListItemClickListener listener) {
		int style = AlertWindow.HAS_MENU;
		AlertWindow aw = new AlertWindow(context, style, lstStr);
		mAlertWindow = aw;
		aw.setItemClickListener(listener);
		aw.show();
	}

	public static void showAlertWindowWithMenu(Context context,
			List<String> lstStr, int offsetX, int offsetY,
			AlertWindow.OnListItemClickListener listener) {
		int style = AlertWindow.HAS_MENU;
		AlertWindow aw = new AlertWindow(context, style, R.style.no_dim_dialog, lstStr);
		mAlertWindow = aw;
		aw.setItemClickListener(listener);
		WindowManager.LayoutParams lp = aw.getWindow().getAttributes();
		lp.gravity = Gravity.LEFT | Gravity.TOP;
		lp.x = offsetX;
		lp.y = offsetY;

		aw.show();
	}

	public static AlertWindow showAlertWindowWithCustomView(Context context,
			View customView, boolean closable, boolean showSoftInput) {
		int style = AlertWindow.HAS_CUSTOM;
		AlertWindow aw = new AlertWindow(context, style, customView);
		mAlertWindow = aw;
		aw.setClosable(closable);
		if (showSoftInput) {
			aw.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		}
		aw.show();
		return aw;
	}


	public static int getTotalHeightofListView(ListView listView) {
		ListAdapter adapter = listView.getAdapter();
		int totalHeight = 0;
		for (int i = 0; i < adapter.getCount(); i++) {
			View view = adapter.getView(i, null, listView);
			view.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			view.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),

					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

			totalHeight += view.getMeasuredHeight();
		}
		totalHeight
		+= (listView.getDividerHeight() * (adapter.getCount() - 1));
		return totalHeight;
	}

	public static void dismissAlertWindow() {
		try {
			if (mAlertWindow != null) {
				mAlertWindow.dismiss();
				mAlertWindow = null;
			}
		} catch (IllegalArgumentException ex) {
			mAlertWindow = null;
		}
	}
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public interface TimePickerListener{
		void timeChecked(int mHour,int mMin);
	}
}
