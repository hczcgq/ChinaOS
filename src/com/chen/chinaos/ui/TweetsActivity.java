package com.chen.chinaos.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TweetsActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView textView=new TextView(this);
		textView.setText("TweetsActivity");
		setContentView(textView);
	}
}
