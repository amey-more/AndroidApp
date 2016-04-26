package com.cmpe277.footballnews;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class LiveScoreFragment extends ListFragment {

	private String URL_ITEMS = "http://football-api.com/api/?Action=today&APIKey=888341c2-0e87-ae4f-15f7cad86e0f&comp_id=1204";
	private static final String TAG_LIVESCORE = "matches";
	private static final String TAG_TEAMA = "match_localteam_name";
	private static final String TAG_TEAMB = "match_visitorteam_name";
	private static final String TAG_SCOREA = "match_localteam_score";
	private static final String TAG_SCOREB = "match_visitorteam_score";
	private static final String TAG_TIME = "match_time";
	private static final String TAG_ERROR = "no matches found today";

	JSONArray liveScore = null;
	ArrayList<HashMap<String, String>> liveScoreList = new ArrayList<HashMap<String, String>>();

	public LiveScoreFragment() {
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
		// setContentView(R.layout.fragment_fixtures);
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
					liveScore = jsonObj.getJSONArray(TAG_LIVESCORE);
					Log.d("json aray", "user point array");
					int len = liveScore.length();
					Log.d("len", "get array length");
					for (int i = 0; i < liveScore.length(); i++) {
						JSONObject c = liveScore.getJSONObject(i);
						// String matchId = c.getString(TAG_MATCHID);
						// Log.d("matchId", matchId);
						String teamA = c.getString(TAG_TEAMA);
						Log.d("teamA", teamA);
						String scoreA = c.getString(TAG_SCOREA);
						Log.d("scoreA", scoreA);
						String scoreB = c.getString(TAG_SCOREB);
						Log.d("scoreB", scoreB);
						String teamB = c.getString(TAG_TEAMB);
						Log.d("teamB", teamB);
						String time = c.getString(TAG_TIME);
						Log.d("time", time);
						// hashmap for single match
						HashMap<String, String> liveScore = new HashMap<String, String>();
						// adding each child node to HashMap key => value
						// matchFixture.put(TAG_MATCHID, matchId);
						liveScore.put(TAG_TEAMA, teamA);
						liveScore.put(TAG_SCOREA, scoreA);
						liveScore.put(TAG_SCOREB, scoreB);
						liveScore.put(TAG_TEAMB, teamB);
						liveScore.put(TAG_TIME, time);
						liveScoreList.add(liveScore);
					}
				} catch (JSONException e) {
					Log.d("catch", "in the catch");
					e.printStackTrace();
					//liveScore.put(TAG_ERROR);


				}
			} else {
				Log.e("JSON Data", "Didn't receive any data from server!");
				//System.out.println("NO DATA1!!!");
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (result == null) {
				Toast toast = Toast.makeText(getActivity(), "No Live matches are currently being played!", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			ListAdapter adapter = new SimpleAdapter(getActivity(),
					liveScoreList, R.layout.results_list_item, new String[] {
				TAG_TEAMA, TAG_SCOREA, TAG_SCOREB, TAG_TEAMB, TAG_TIME },
				new int[] { R.id.teamA, R.id.scoreA, R.id.scoreB,
				R.id.teamB, R.id.time });
			getListView().setBackgroundColor(getResources().getColor(R.color.list_bg));
			setListAdapter(adapter);
		}
	}

	/*
	 * public boolean onCreateOptionsMenu(Menu menu) {
	 * getMenuInflater().inflate(R.menu.activity_main, menu); return true; }
	 */

}
