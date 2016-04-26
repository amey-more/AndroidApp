package com.cmpe277.footballnews.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cmpe277.footballnews.R;
import com.cmpe277.footballnews.model.NewsItem;

public class NewsListAdapter extends BaseAdapter {
	
	ArrayList<NewsItem> mNewsList;
	Context mContext;

	public NewsListAdapter(Context context, int resource, ArrayList<NewsItem> newsList) {
		mNewsList = newsList;
		mContext  = context;
	}
	
	@Override
	public int getCount() {
		return mNewsList.size();
	}

	@Override
	public NewsItem getItem(int position) {
		return mNewsList.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		
		if (null == vi) {
			vi = LayoutInflater.from(mContext).inflate(R.layout.news_list_item, null);
		}
		final NewsItem item = getItem(position);
		TextView titleView = (TextView) vi.findViewById(R.id.title);
		TextView descriptionView = (TextView) vi.findViewById(R.id.description);
		TextView dateView = (TextView) vi.findViewById(R.id.date);
		
		titleView.setText(item.getTitle());
		descriptionView.setText(item.getDescription());
		dateView.setText(item.getDate());
		vi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(item.getLink()));
				mContext.startActivity(i);
			}
		});
		
		return vi;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
