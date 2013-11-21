package com.chen.chinaos.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnCheckedChangeListener{

	private TabHost tabHost;
	private RadioGroup radioGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		tabHost=getTabHost();
		tabHost.addTab(addtabSpec("News", NewsActivity.class));
		tabHost.addTab(addtabSpec("Questions", QuestionsActivity.class));
		tabHost.addTab(addtabSpec("Tweets", TweetsActivity.class));
		tabHost.addTab(addtabSpec("Actives", ActivesActivity.class));
		tabHost.addTab(addtabSpec("More", MoreActivity.class));
		
		setCurrentTab();
		
		radioGroup=(RadioGroup) findViewById(R.id.main_linearlayout_footer);
		radioGroup.setOnCheckedChangeListener(this);
		
		
	}
	
	/**
	 * 设置默认显示
	 */
	private void setCurrentTab() {
		tabHost.setCurrentTab(0);
		RadioButton radio_button0 = (RadioButton) findViewById(R.id.main_footbar_news);
		radio_button0.setChecked(true);
	}

	/**
	 * 添加tab
	 * @param tag
	 * @param context
	 * @return
	 */
	private TabSpec addtabSpec(String tag, Class<?> context) {
		return tabHost.newTabSpec(tag).setIndicator(tag).setContent(new Intent(this,context));
	}

	
	/**
	 * RadioGroup选中监听
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int id) {
		switch (id) {
		case R.id.main_footbar_news:
			tabHost.setCurrentTabByTag("News");
			break;
		case R.id.main_footbar_question:
			tabHost.setCurrentTabByTag("Questions");
			break;
		case R.id.main_footbar_tweet:
			
			tabHost.setCurrentTabByTag("Tweets");
			break;
		case R.id.main_footbar_active:
			tabHost.setCurrentTabByTag("Actives");
			break;
		case R.id.main_footbar_more:
			tabHost.setCurrentTabByTag("More");
			break;
		}
	}

	
}
