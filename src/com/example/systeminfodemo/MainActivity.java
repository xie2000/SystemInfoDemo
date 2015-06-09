package com.example.systeminfodemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView textView = (TextView) findViewById(R.id.testView);
		findViewById(R.id.testBtn).setOnClickListener(this);

		String str = "";
		str = str + "getIMEI:" + SystemInfo.getIMEI(this) + "\n";
		str = str + "getIMSI:" + SystemInfo.getIMSI(this) + "\n";
		str = str + "getPhoneModel:" + SystemInfo.getPhoneModel() + "\n";
		str = str + "getPhoneSdkVersionInt:"
				+ SystemInfo.getPhoneSdkVersionInt() + "\n";
		str = str + "getPhoneSDKVersionChar:"
				+ SystemInfo.getPhoneSDKVersionChar() + "\n";
		str = str + "getAppVersionChars:" + SystemInfo.getAppVersionChars(this)
				+ "\n";
		str = str + "getAppVersionInt:" + SystemInfo.getAppVersionInt(this)
				+ "\n";
		str = str + "getLocalIpAddress:" + SystemInfo.getLocalIpAddress()
				+ "\n";

		textView.setText(str);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String str = "GetNetIp:" + SystemInfo.GetNetIp();
				Message msg = Message.obtain();
				msg.what = 0;
				msg.obj = str;
			}
		}).start();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String str = (String) msg.obj;
			Button btn = (Button) findViewById(R.id.testBtn);
			btn.setText(str);
		};
	};
}
