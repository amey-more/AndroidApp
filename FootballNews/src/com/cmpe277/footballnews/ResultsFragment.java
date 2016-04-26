package com.cmpe277.footballnews;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ListFragment;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class ResultsFragment extends ListFragment {

	private String URL_ITEMS = "http://football-api.com/api/?Action=fixtures&APIKey=888341c2-0e87-ae4f-15f7cad86e0f&comp_id=1204&from_date=04.10.2014&to_date=05.10.2014";
	private static final String TAG_FIXTURE = "matches";
	private static final String TAG_TEAMA = "match_localteam_name";
	private static final String TAG_TEAMB = "match_visitorteam_name";
	private static final String TAG_SCOREA = "match_localteam_score";
	private static final String TAG_SCOREB = "match_visitorteam_score";

	JSONArray matchResult = null;
	ArrayList<HashMap<String, String>> matchResultList = new ArrayList<HashMap<String, String>>();

	public ResultsFragment() {
	}

	/*
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container,Bundle savedInstanceState) {
	 * 
	 * View rootView = inflater.inflate(R.layout.fragment_fixtures, container,
	 * false);
	 * 
	 * return rootView; }
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.fragment_fixtures);
		setRetainInstance(true);
		new GetFixture().execute();
	}

	private class GetFixture extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg) {
			ServiceHandler serviceClient = new ServiceHandler();
			Log.d("url: ", "> " + URL_ITEMS);
			String json = serviceClient.makeServiceCall(URL_ITEMS,
					ServiceHandler.GET);
			// print the json response in the log
			Log.d("Get match fixture response: ", "> " + json);
			if (json != null) {
				try {
					Log.d("try", "in the try");
					JSONObject jsonObj = new JSONObject(json);
					Log.d("jsonObject", "new json Object");
					// Getting JSON Array node
					matchResult = jsonObj.getJSONArray(TAG_FIXTURE);
					Log.d("json aray", "user point array");
					int len = matchResult.length();
					Log.d("len", "get array length");
					for (int i = 0; i < matchResult.length(); i++) {
						JSONObject c = matchResult.getJSONObject(i);
//						String matchId = c.getString(TAG_MATCHID);
//						Log.d("matchId", matchId);
						String teamA = c.getString(TAG_TEAMA);
						Log.d("teamA", teamA);
						String scoreA = c.getString(TAG_SCOREA);
						Log.d("scoreA", scoreA);
						String scoreB = c.getString(TAG_SCOREB);
						Log.d("scoreB", scoreB);
						String teamB = c.getString(TAG_TEAMB);
						Log.d("teamB", teamB);
						// hashmap for single match
						HashMap<String, String> matchFixture = new HashMap<String, String>();
						// adding each child node to HashMap key => value
						//matchFixture.put(TAG_MATCHID, matchId);
						matchFixture.put(TAG_TEAMA, teamA);
						matchFixture.put(TAG_SCOREA, scoreA);
						matchFixture.put(TAG_SCOREB, scoreB);
						matchFixture.put(TAG_TEAMB, teamB);
						matchResultList.add(matchFixture);
					}
				} catch (JSONException e) {
					Log.d("catch", "in the catch");
					e.printStackTrace();
				}
			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ListAdapter adapter = new SimpleAdapter(getActivity(),
					matchResultList, R.layout.results_list_item, new String[] {
							 TAG_TEAMA,TAG_SCOREA,TAG_SCOREB, TAG_TEAMB }, new int[] {
							 R.id.teamA,R.id.scoreA,R.id.scoreB, R.id.teamB });
			getListView().setBackgroundColor(getResources().getColor(R.color.list_bg));
			int[] colors = {0, 0xFFFFFFFF, 0}; // red for the example
			getListView().setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
			getListView().setDividerHeight(1);

			setListAdapter(adapter);
		}
	}

/*	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}*/

}
