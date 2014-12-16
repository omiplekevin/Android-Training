package com.example.listviewsample;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	ArrayAdapter<String> adapter;
	CustomListAdapter customAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView1);

		adapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.simple_list_item_content, new String[] { "Item 1",
						"Item 2", "Item 3", "Item 4", "Item 5", "Item 6" });
		
		List<CustomContentModel> contentModels = new ArrayList<CustomContentModel>();
		CustomContentModel model = null;
		for (int i = 0; i < 10; i++) {
			model = new CustomContentModel();
			model.textContent = "Content " + (i + 1);
			model.res = android.R.drawable.btn_star;
			contentModels.add(model);
		}
		
		customAdapter = new CustomListAdapter(getApplicationContext(),
				contentModels);

		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_simple) {
			listView.setAdapter(adapter);
			listView.invalidate();
			return true;
		} else {
			listView.setAdapter(customAdapter);
			listView.invalidate();
			return true;
		}
	}
}
