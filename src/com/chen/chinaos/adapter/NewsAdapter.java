package com.chen.chinaos.adapter;

import java.util.List;

import com.chen.chinaos.app.StringUtils;
import com.chen.chinaos.bean.News;
import com.chen.chinaos.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	private Context context;
	private List<News> newsLists;
	private LayoutInflater inflater;

	public NewsAdapter(Context context, List<News> newsLists) {
		this.context = context;
		this.newsLists = newsLists;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return newsLists.size();
	}

	@Override
	public Object getItem(int postion) {
		return newsLists.get(postion);
	}

	@Override
	public long getItemId(int postion) {
		return postion;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.news_listitem, null);
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.author = (TextView) view.findViewById(R.id.author);
			holder.data = (TextView) view.findViewById(R.id.data);
			holder.commentCount = (TextView) view
					.findViewById(R.id.commentCount);
			holder.flag = (ImageView) view.findViewById(R.id.flag);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		News news = newsLists.get(position);
		holder.title.setText(news.getTitle());
		holder.author.setText(news.getAuthor());
		holder.commentCount.setText(news.getCommentCount());
		holder.data.setText(StringUtils.friendly_time(news.getPubDate()));

		if (StringUtils.isToday(news.getPubDate())) {
			holder.flag.setVisibility(View.VISIBLE);
		} else {
			holder.flag.setVisibility(View.GONE);
		}
		return view;
	}

	class ViewHolder {
		TextView title;
		TextView author;
		TextView data;
		TextView commentCount;
		ImageView flag;
	}

}
