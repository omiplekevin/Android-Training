package com.example.transientlistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;


public class MainActivity extends Activity{
	
	ListView listView;
	ListViewAdpater adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listView = (ListView)findViewById(R.id.listView);
        adapter = new ListViewAdpater();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				view.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private class ListViewAdpater extends BaseAdapter{

		@Override
		public int getCount() {
			return 100;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			
			if(convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(getApplicationContext(), R.layout.list_item_view, null);
				convertView.setHasTransientState(true);
				holder.imageView1 = (ImageView)convertView.findViewById(R.id.imageView1);
				holder.imageView2 = (ImageView)convertView.findViewById(R.id.imageView2);
				convertView.setAlpha(0.0f);
				
				holder.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.android_cube));
				holder.imageView1.animate().alpha(1.0f);
				holder.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ilocos));
				holder.imageView2.animate().alpha(1.0f);
				
				convertView.animate().alpha(1.0f).setDuration(1000);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			return convertView;
		}
		
		class ViewHolder{
			ImageView imageView1;
			ImageView imageView2;
		}
    	
    }
}
