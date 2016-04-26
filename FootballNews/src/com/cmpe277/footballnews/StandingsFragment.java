package com.cmpe277.footballnews;

import java.util.ArrayList;
import java.util.HashMap;

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

public class StandingsFragment extends ListFragment {
	private String URL_ITEMS = "http://football-api.com/api/?Action=standings&APIKey=888341c2-0e87-ae4f-15f7cad86e0f&comp_id=1204";
	private static final String TAG_STANDINGS = "teams";
	private static final String TAG_POSITION = "stand_position";
	private static final String TAG_NAME = "stand_team_name";
	private static final String TAG_GP = "stand_overall_gp";
	private static final String TAG_WON = "stand_overall_w";
	private static final String TAG_LOST = "stand_overall_l";
	private static final String TAG_DRAWN = "stand_overall_d";
	private static final String TAG_PTS = "stand_points";

	JSONArray standings = null;
	ArrayList<HashMap<String, String>> standingsList = new ArrayList<HashMap<String, String>>();

	public StandingsFragment() {
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
					standings = jsonObj.getJSONArray(TAG_STANDINGS);
					Log.d("json aray", "user point array");
					int len = standings.length();
					Log.d("len", "get array length");
					for (int i = 0; i < standings.length(); i++) {
						JSONObject c = standings.getJSONObject(i);
//						String matchId = c.getString(TAG_MATCHID);
//						Log.d("matchId", matchId);
						String position = c.getString(TAG_POSITION);
						Log.d("position", position);
						String name = c.getString(TAG_NAME);
						Log.d("name", name);
						String gp = c.getString(TAG_GP);
						Log.d("gp", gp);
						String won = c.getString(TAG_WON);
						Log.d("won", won);
						String lost = c.getString(TAG_LOST);
						Log.d("lost", lost);
						String drawn = c.getString(TAG_DRAWN);
						Log.d("drawn", drawn);
						String pts = c.getString(TAG_PTS);
						Log.d("pts", pts);
						
						// hashmap for single match
						HashMap<String, String> standings = new HashMap<String, String>();
						// adding each child node to HashMap key => value
						//matchFixture.put(TAG_MATCHID, matchId);
						standings.put(TAG_POSITION, position);
						standings.put(TAG_NAME, name);
						standings.put(TAG_GP, gp);
						standings.put(TAG_WON, won);
						standings.put(TAG_LOST, lost);
						standings.put(TAG_DRAWN, drawn);
						standings.put(TAG_PTS, pts);
						standingsList.add(standings);
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
					standingsList, R.layout.standings_list_item, new String[] {
							 TAG_POSITION,TAG_NAME,TAG_GP,TAG_WON,TAG_LOST,TAG_DRAWN,TAG_PTS  }, new int[] {
							 R.id.matchId,R.id.team,R.id.played, R.id.won,R.id.lost,R.id.drawn,R.id.pts });
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
