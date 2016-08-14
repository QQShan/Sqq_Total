package com.sqq.sqq_total.view;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sqq.sqq_total.R;

/**
 *
 */
public class Dialog {

	public static final int error = 0;
	public static final int warn = 1;
	public static final int tip = 2;
	public static final int doubt = 3;
	public static final int right = 4;
	public static Dialogcallback callback;
	private static boolean touchcancel;

	/**
	 * 
	 * @param context
	 * @param title
	 *            标题
	 * @param Mess
	 *            提示信息
	 * @param touchcancelable
	 *            点击非窗体消失
	 * @param lastMS
	 *            持续时间
	 */

	public static void showDialog(Context context, String title, String Mess,
			boolean touchcancelable, int lastMS) {
		if(title==null){
			title = "";
		}
		if(title.equals("")){
			title = context.getString(R.string.dialog_tip);
			if(title==null){
				title = "";
			}
		}
		
		touchcancel = touchcancelable;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(Mess);

		builder.setCancelable(touchcancelable);
		final AlertDialog dialog = builder.create();

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				if (callback != null)
					callback.dialogDismiss();
			}
		});

		dialog.show();

		if (lastMS != 0) {

			ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
			scheduledExecutorService.schedule(new Runnable() {
				@Override
				public void run() {
					Log.d("Client", "探访那卡规");
					if (dialog.isShowing()) {
						dialog.dismiss();
						if (callback != null)
							callback.timeOut();
					}
				}
			}, lastMS, TimeUnit.MILLISECONDS);
		}


	}

	public interface Dialogcallback {

		/**
		 * 超时
		 */
		public void timeOut();
		/**
		 * 对话框消失
		 */
		public void dialogDismiss();
	}

	public static void setDialogcallback(Dialogcallback callback) {
		Dialog.callback = callback;
	}
}
