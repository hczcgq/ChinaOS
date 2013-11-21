package com.chen.chinaos.ui;

import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view=LayoutInflater.from(this).inflate(R.layout.splash, null);
		setContentView(view);
		
		AlphaAnimation animation=new AlphaAnimation(0.2f, 1.0f);
		animation.setDuration(2000);
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent=new Intent(SplashActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
	
	}
}
