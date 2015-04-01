package app.and.felicity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ScheduleFillerHelper {

	public static final String API_CALL = "http://felicity.iiit.ac.in/api/events/?last_id=";
	public static final String SCHEDULE_CACHE_ID = "scheduleCacheId";
	public static final String SCHEDULE_CACHE_DATA = "scheduleCacheData";
	public static ScheduleItemAdapter siadapterGlobal = null;
	public static ProgressDialog progress;
	public static SharedPreferences sp;

	static public class ScheduleListItem {
		// Should replicate
		// {"category":"threads","path":"gordian-knot","name":"Gordian Knot",
		// "start_date":"2015-1-17","start_time":"12:00 AM","end_date":"2015-1-19",
		// "end_time":"12:00 AM"}

		public String category, path, name, startDate, startTime, endDate, endTime;

		public ScheduleListItem (String category, String path, 
				String name, String startDate, String startTime, 
				String endDate, String endTime) {

			this.category = category;
			this.path = path;
			this.name = name;
			this.startDate = startDate;
			this.startTime = startTime;
			this.endDate = endDate;
			this.endTime = endTime;
		}
	}

	static public class ScheduleItemAdapter extends ArrayAdapter<ScheduleListItem> {
		public ScheduleItemAdapter(Context context, ArrayList<ScheduleListItem> eventListItems) {
			super(context, 0, eventListItems);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Get the data item for this position
			ScheduleListItem sli = getItem(position);
			// Check if an existing view is being reused, otherwise inflate the view
			if (convertView == null) {
				// Inflate the view from item
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_item, parent, false);
			}

			// Get all the views to fill with data
			TextView title = (TextView) convertView.findViewById(R.id.schedule_item_title);
			TextView dates = (TextView) convertView.findViewById(R.id.schedule_item_dates);
			TextView category = (TextView) convertView.findViewById(R.id.schedule_item_category);
			TextView timings = (TextView) convertView.findViewById(R.id.schedule_item_timings);

			// Fill all the views with data
			title.setText(sli.name);
			dates.setText(sli.startDate+" : "+sli.endDate);
			category.setText("-"+sli.category);
			timings.setText(sli.startTime+" : "+sli.endTime);

			// Return the completed view to render on screen			
			return convertView;
		}
	}

	public static void fillSchedule(ListView lv) {
		// Calling this with a list view inflates and adds adapters to it
		Log.d("mytag", "filling schedule");

		ArrayList<ScheduleListItem> eventsListArray = new ArrayList<ScheduleListItem>();
		ScheduleItemAdapter siadapter = new ScheduleItemAdapter(HomeActivity.context, eventsListArray);
		lv.setAdapter(siadapter);
		siadapterGlobal = siadapter;

		JSONArray scheduleData = getScheduleJsonArray();

		// Filling it with some old data, call for new data next!
		if(scheduleData!=null) {
			fillListViewFromJsonArray(scheduleData);
		}
		else {
			// Filling for the first time, we show dialog till the network call is complete and then remove it...
			progress = new ProgressDialog(HomeActivity.context);
			progress.setTitle("Loading");
			progress.setMessage("Loading the schedule");
			progress.setCancelable(false);
			progress.show();
		}
		new doScheduleCall().execute(API_CALL, getScheduleCacheId());

		// progress = ProgressDialog.show(HomeActivity.context, "dialog title", "dialog message", true);
	}

	public static class doScheduleCall extends AsyncTask<String, Void, Void> {
		String response;
		ResponseHandler<String> handler;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params){
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]+params[1]);
			handler = new BasicResponseHandler();
			// Execute HTTP Post Request
			try {
				response = httpclient.execute(httppost, handler);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			try {
				if(response == null)
					return;
				else if(response.equals("false")) {
					// Toast.makeText(HomeActivity.context, "No update", Toast.LENGTH_LONG).show();
					Log.d("mytag", "No update");
					return;
				}
				// Parsing the response
				JSONObject reply = new JSONObject(response);

				ScheduleFillerHelper.setScheduleCacheIdAndJsonArray(reply.getString("last_id"), reply.getJSONArray("data"));
				ScheduleFillerHelper.fillListViewFromJsonArray(reply.getJSONArray("data"));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(ScheduleFillerHelper.progress != null) {
				ScheduleFillerHelper.progress.dismiss();
			}
		}
	}

	public static String getScheduleCacheId() {
		// TODO Auto-generated method stub
		sp = PreferenceManager.getDefaultSharedPreferences(HomeActivity.context);
		return sp.getString(SCHEDULE_CACHE_ID, "");
	}

	public static JSONArray getScheduleJsonArray() {
		// TODO Auto-generated method stub
		sp = PreferenceManager.getDefaultSharedPreferences(HomeActivity.context);
		JSONArray jarray = null;
		try {
			jarray = new JSONArray(sp.getString(SCHEDULE_CACHE_DATA, ""));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jarray;
	}

	public static String setScheduleCacheIdAndJsonArray(String cacheId, JSONArray jarray) {
		// TODO Auto-generated method stub
		sp = PreferenceManager.getDefaultSharedPreferences(HomeActivity.context);
		sp.edit().putString(SCHEDULE_CACHE_ID, cacheId).commit();
		sp.edit().putString(SCHEDULE_CACHE_DATA, jarray.toString()).commit();
		return null;
	}

	public static void fillListViewFromJsonArray(JSONArray jarray) {
		if(siadapterGlobal == null) {
			Log.d("mytag", "this is just not fucking possible!!");
			return;
		}
		if(jarray == null) {
			Log.d("mytag", "almost parsed a null object there, phew!");
			return;
		}
		siadapterGlobal.clear();
		int len = jarray.length();
		for(int i=0; i<len ; i++) {
			try {
				JSONObject jsonObj = jarray.getJSONObject(i);
				String category, path, name, startDate, startTime, endDate, endTime;
				category = jsonObj.getString("category");
				path = jsonObj.getString("path");
				name = jsonObj.getString("name");
				startDate = jsonObj.getString("start_date");
				startTime = jsonObj.getString("start_time");
				endDate = jsonObj.getString("end_date");
				endTime = jsonObj.getString("end_time");
				ScheduleListItem sli = new ScheduleListItem(category, path, name, startDate, startTime, endDate, endTime);
				siadapterGlobal.add(sli);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
