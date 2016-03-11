package com.sheplu.suplink;

import java.io.IOException;
import java.util.HashMap;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StatActivity extends Activity {
	String user;
	String id;
	TextView name;
	TextView original;
	TextView shortened;
	TextView click;
	TextView created;
	TextView status;
	Button nav;
	Button stats;
	Button toggle;
	Button remove;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stat);
		JSONResource result;
		String stats_list = null;
		JSONObject jObject = null;
		JSONArray jArray = null;
		HashMap<String,String> tmp = null;
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			user = extras.getString("user");
			id = extras.getString("id");
		}
		else {
			finish();
		}
		
		System.out.println(user + " - "+ id);
		
		Resty r = new Resty();
		try {
			result = r.json("http://www.shep.fr/mobile/"+user+"/"+id+"/stat");
			stats_list = result.toObject().toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			jObject = new JSONObject(stats_list);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		try {
			jArray = jObject.getJSONArray("link");
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
				tmp.put("original", oneObject.getString("original"));
				tmp.put("shortened", oneObject.getString("shortened"));
				tmp.put("click", oneObject.getString("click"));
				tmp.put("created", oneObject.getString("created"));
				tmp.put("enable", oneObject.getString("enable"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		name = (TextView)findViewById(R.id.name);
		name.setText("Nom : "+tmp.get("name"));
		original = (TextView)findViewById(R.id.original);
		original.setText("URL : "+tmp.get("original"));
		shortened = (TextView)findViewById(R.id.shortened);
		shortened.setText("URL raccourcie : "+tmp.get("shortened"));
		click = (TextView)findViewById(R.id.click);
		click.setText("Nombre de clics : "+tmp.get("click"));
		created = (TextView)findViewById(R.id.created);
		created.setText("Date : "+tmp.get("created"));
		status = (TextView)findViewById(R.id.status);
		status.setText("Statut : "+tmp.get("enable"));
		
		nav = (Button) findViewById(R.id.nav);
		nav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		Uri uri = Uri.parse("http://www.shep.fr/"+shortened);
        		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        		startActivity(intent);
            }
        });
		
		stats = (Button) findViewById(R.id.stats);
		stats.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		Uri uri = Uri.parse("http://www.shep.fr/user/link/stat");
        		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        		startActivity(intent);
            }
        });
		
		toggle = (Button) findViewById(R.id.toggle);
		toggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	JSONResource result;
            	Resty r = new Resty();
            	try {
					result = r.json("http://www.shep.fr/mobile/"+user+""+id+"/enable");
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
		
		remove = (Button) findViewById(R.id.remove);
		remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	JSONResource result;
            	Resty r = new Resty();
            	try {
					result = r.json("http://www.shep.fr/mobile/"+user+""+id+"/remove");
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stat, menu);
		return true;
	}
}
