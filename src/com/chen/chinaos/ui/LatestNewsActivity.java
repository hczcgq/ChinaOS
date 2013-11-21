package com.chen.chinaos.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.chen.chinaos.adapter.NewsAdapter;
import com.chen.chinaos.app.AppConfig;
import com.chen.chinaos.app.AppContext;
import com.chen.chinaos.app.ConnectServer;
import com.chen.chinaos.app.Constants;
import com.chen.chinaos.bean.News;
import com.chen.chinaos.customview.RefreshableListView;
import com.chen.chinaos.customview.RefreshableListView.OnRefreshListener;
import com.chen.chinaos.parse.NewsListParse;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class LatestNewsActivity extends Activity {
	private RefreshableListView listView;
	private int currentPage = 0; // 当前页
	private AppContext appContext;
	private List<News> news = new ArrayList<News>();
	private List<News> newsAll = new ArrayList<News>();
	
	private boolean isRefresh=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commen_list);

		appContext = (AppContext) getApplication();
		listView = (RefreshableListView) findViewById(R.id.frame_listview_news);

		if (newsAll.isEmpty()) {
			MyTask task = new MyTask();
			task.execute(currentPage);
		}
		
		listView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(RefreshableListView listView) {
				isRefresh=true;
				MyTask task=new MyTask();
				task.execute(currentPage++);
			}
		});
	}

	class MyTask extends AsyncTask<Integer, Void, String> {

		@Override
		protected String doInBackground(Integer... params) {
			int currentPage = params[0];
			String url = Constants.NEWS_LIST_URL + "?pageSize="
					+ Constants.PAGE_SIZE + "&catalog="
					+ Constants.CATALOG_NEWS + "&pageIndex=" + currentPage;
			return new ConnectServer().ConnectByGet(appContext, url);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				NewsListParse parse = new NewsListParse();
				try {
					InputStream in = new ByteArrayInputStream(
							result.getBytes("utf-8"));
					news = parse.ParseXML(in);
					newsAll.addAll(news);
					NewsAdapter adapter = new NewsAdapter(
							LatestNewsActivity.this, newsAll);
					if(isRefresh){
						listView.completeRefreshing();
						adapter.notifyDataSetChanged();
					}else{
						listView.setAdapter(adapter);
					}
					System.out.println(newsAll.size());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				AppConfig.showShortToast(LatestNewsActivity.this, "获取资讯失败");
			}
		}

		@Override
		protected void onPreExecute() {
//			if(isRefresh){
//				listView.completeRefreshing();
//			}
		}
	}
}
