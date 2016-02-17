package com.example.asyncsample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.asyncState);

		startAsync();
		// startThread(10);
	}

	public void startAsync() {
		new AsyncTask<Void, Integer, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub

				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(1000);
						publishProgress(i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				return null;
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub

				textView.setText(values[0] + "");

				super.onProgressUpdate(values);
			}

		}.execute();
	}

	/*public void startThread(int x) {
		Runnable run = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int x = 0; x < 10000; x++) {
					updateText(x + "");
				}
			}
		};
		Handler handler = new Handler();
		handler.postDelayed(run, 10);
	}

	private void updateText(String x) {
		textView.setText(x);
	}*/

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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
