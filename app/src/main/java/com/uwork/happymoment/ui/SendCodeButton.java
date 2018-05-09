package com.uwork.happymoment.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class SendCodeButton extends Button {

	private final int WAIT_TIME = 60;
	private int wait = WAIT_TIME;

	public SendCodeButton(Context context) {
		super(context);
		initView();

	}

	public SendCodeButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public SendCodeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		setPadding(0, 0, 0, 0);
		setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		initDefaultStatue();
	}

	private void initDefaultStatue() {
		setText("获取验证码");
	}

	public void startTime() {
		handler.sendEmptyMessage(1);
	}

	public void resumeTime(){
		handler.removeMessages(1);
		handler.sendEmptyMessage(2);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what){
				case 1:
					if (wait > 0) {
						setEnabled(false);
						handler.sendEmptyMessageDelayed(1, 1000);
						setText(wait + "s");
						wait--;
					} else {
						wait = WAIT_TIME;
						initDefaultStatue();
						setEnabled(true);
					}
					break;
				case 2:
					wait = WAIT_TIME;
					initDefaultStatue();
					setEnabled(true);
					break;
			}
		};
	};

	public void close(){
		if (handler != null){
			handler.removeMessages(1);
			handler.removeMessages(2);
		}
	}

}