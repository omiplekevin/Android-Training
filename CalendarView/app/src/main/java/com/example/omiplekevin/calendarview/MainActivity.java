package com.example.omiplekevin.calendarview;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.roomorama.caldroid.CaldroidFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeCalendarFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeCalendarFragment(){
        Calendar calendar = Calendar.getInstance();
        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CaldroidFragment.MONTH, calendar.get(Calendar.MONTH) + 1);
        bundle.putInt(CaldroidFragment.YEAR, calendar.get(Calendar.YEAR));
        caldroidFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.calendarFrame, caldroidFragment);
        fragmentTransaction.commit();


    }
}
