package com.sheplu.suplink;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class DashActivity extends ListActivity implements OnItemClickListener{

	static final ArrayList<HashMap<String,String>> list = 
			new ArrayList<HashMap<String,String>>(); 
	ListView links;
	
	String user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash);

		SimpleAdapter adapter = new SimpleAdapter(
				this, 
				list,
				R.layout.custom_row_view,
				new String[] {"name","date","url"},
				new int[] {R.id.text1,R.id.text2, R.id.text3}
				);
		populateList();
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dash, menu);
		return true;
	}

	private void populateList() {

		user = null;
		JSONResource result;
		String links_list = null;
		JSONObject jObject = null;
		JSONArray jArray = null;
		HashMap<String,String> tmp;

		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			user = extras.getString("user");
		}
		else {
			finish();
		}
		
		list.clear();

		Resty r = new Resty();
		try {
			result = r.json("http://www.shep.fr/mobile/"+user+"/dashboard");
			links_list = result.toObject().toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			jObject = new JSONObject(links_list);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		try {
			jArray = jObject.getJSONArray("links");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		for (int i=0; i < jArray.length(); i++)
		{
			JSONObject oneObject = null;
			try {
				oneObject = jArray.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				tmp = new HashMap<String,String>();
				tmp.put("id", oneObject.getString("id"));
				tmp.put("name", oneObject.getString("name"));
				tmp.put("date", oneObject.getString("created"));
				tmp.put("url", oneObject.getString("original"));
				list.add(tmp);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String item = (String)list.get((int) id).get("id");
		Intent intent = new Intent(this,StatActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("id", item);
        startActivity(intent);
	}

}
