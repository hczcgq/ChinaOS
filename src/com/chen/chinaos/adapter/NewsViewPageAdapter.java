package com.chen.chinaos.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

public class NewsViewPageAdapter extends PagerAdapter {

	private ArrayList<View> pageViews;
	public NewsViewPageAdapter(ArrayList<View> pageViews) {
		this.pageViews=pageViews;
	}

	@Override
	public int getCount() {
		return pageViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(pageViews.get(arg1));
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(pageViews.get(arg1));
		return pageViews.get(arg1);
	}
}
