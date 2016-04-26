package com.cmpe277.footballnews;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListFragment;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.cmpe277.footballnews.adapter.NewsListAdapter;
import com.cmpe277.footballnews.model.NewsItem;

public class HomeFragment extends ListFragment {

	private static final String URL = "http://api.usatoday.com/open/articles/topnews/sports?count=20&days=0&page=0&encoding=json&api_key=t76gzq4xv74xaxe26zqhz4dr";

	private ArrayList<NewsItem> mNewsList = new ArrayList<NewsItem>();
	public HomeFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.fragment_fixtures);
		setRetainInstance(true);
		new SportsNewsTask().execute();
	}

	private class SportsNewsTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg) {
			ServiceHandler serviceClient = new ServiceHandler();
			Log.d("url: ", "> " + URL);
			String json = serviceClient
					.makeServiceCall(URL, ServiceHandler.GET);
			// print the json response in the log
			Log.d("Get match fixture response: ", "> " + json);
			if (json != null) {
				try {
					Log.d("try", "in the try");
					JSONObject jsonObj = new JSONObject(json);
					Log.d("jsonObject", "new json Object");
					// Getting JSON Array node
					JSONArray stories = jsonObj.getJSONArray("stories");
					Log.d("json aray", "user point array");
					int len = stories.length();
					Log.d("len", "get array length");
					mNewsList.clear();
					for (int i = 0; i < len; i++) {
						JSONObject story = stories.getJSONObject(i);
						NewsItem item = new NewsItem();
						item.setDescription(story.getString("description"));
						item.setTitle(story.getString("title"));
						item.setLink(story.getString("link"));
						item.setDate(story.getString("pubDate"));
						mNewsList.add(item);
					}
				} catch (JSONException e) {
					Log.d("catch", "in the catch", e);
				}
			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			getListView().setBackgroundColor(getResources().getColor(R.color.list_bg));
			int[] colors = {0, 0xFFFFFFFF, 0}; // red for the example
			getListView().setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
			getListView().setDividerHeight(1);
			
			NewsListAdapter adapter = new NewsListAdapter(getActivity(), R.layout.news_list_item, mNewsList);
			setListAdapter(adapter);
		}
	}
}
