package com.sheplu.suplink;

import java.io.IOException;
import java.util.ArrayList;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") public class MainActivity extends Activity implements OnItemClickListener {

	ListView users;
	
    @SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.GINGERBREAD) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        
        JSONResource result;
        String users_list = null;
        JSONObject jObject = null;
        JSONArray jArray = null;
        ArrayList<String> username_list = new ArrayList<String>();
        
        Resty r = new Resty();
		try {
			result = r.json("http://www.shep.fr/mobile/all");
			users_list = result.toObject().toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			jObject = new JSONObject(users_list);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		try {
			jArray = jObject.getJSONArray("users");
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
				String oneObjectsItem2 = oneObject.getString("username");
				username_list.add(oneObjectsItem2);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		users = (ListView) findViewById(R.id.user_list);
		ArrayAdapter<String> arrayAdapter =      
		         new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, username_list);
		users.setAdapter(arrayAdapter); 
        
		users.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String item = ((TextView)view).getText().toString();
        Intent intent = new Intent(this,DashActivity.class);
        intent.putExtra("user", item);
        startActivity(intent);
	}
}
