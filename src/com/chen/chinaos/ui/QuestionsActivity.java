package com.chen.chinaos.ui;

import java.util.ArrayList;

import com.chen.chinaos.adapter.NewsViewPageAdapter;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class QuestionsActivity extends ActivityGroup{

	private Button answer, share, composite,career,office;
	private ViewPager viewPager;
	private ArrayList<View> pageViews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions);

		answer = (Button) findViewById(R.id.answer);
		share = (Button) findViewById(R.id.share);
		composite = (Button) findViewById(R.id.composite);
		career = (Button) findViewById(R.id.career);
		office = (Button) findViewById(R.id.office);
		
		
		Button[] btns= { answer, share, composite,career,office};
		
		viewPager = (ViewPager) findViewById(R.id.content);
		
		initViewpage();
		
		viewPager.setAdapter(new NewsViewPageAdapter(pageViews));
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				selsct(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		
		for (int i = 0; i < 5; i++) {
			final int a = i;
			btns[a].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					viewPager.setCurrentItem(a);
					selsct(a);
				}
			});
		}
		selsct(0);
		
	}

	/**
	 * 设置选中字体颜色
	 * @param i
	 */
	protected void selsct(int i) {
		Button[] btns= { answer, share, composite,career,office};
		for (int n = 0; n < 5; n++) {
			final int a = n;
			if (i == a) {
				btns[a].setTextColor(Color.parseColor("#ffffff"));
			} else {
				btns[a].setTextColor(Color.parseColor("#060606"));
			}
		}
	}

	/**
	 *初始化viewPage页面
	 */
	private void initViewpage() {
		pageViews = new ArrayList<View>();
		View view1 = getLocalActivityManager().startActivity("activity1",
				new Intent(this, AnswerActivity.class)).getDecorView();
		View view2 = getLocalActivityManager().startActivity("activity2",
				new Intent(this, ShareBlogactivity.class)).getDecorView();
		View view3 = getLocalActivityManager().startActivity("activity3",
				new Intent(this, CompositeActivity.class)).getDecorView();
		View view4 = getLocalActivityManager().startActivity("activity4",
				new Intent(this, CareerActivity.class)).getDecorView();
		View view5 = getLocalActivityManager().startActivity("activity5",
				new Intent(this, OfficeActivity.class)).getDecorView();
		pageViews.add(view1);
		pageViews.add(view2);
		pageViews.add(view3);
		pageViews.add(view4);
		pageViews.add(view5);
	}
}
