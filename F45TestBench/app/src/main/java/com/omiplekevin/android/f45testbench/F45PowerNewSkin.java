package com.omiplekevin.android.f45testbench;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.omiplekevin.android.f45testbench.fragment.ControllerFragment;
import com.omiplekevin.android.f45testbench.fragment.TimelineFragment;
import com.omiplekevin.android.f45testbench.interfaces.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMIPLEKEVIN on 028 Mar 28, 2017.
 */

public class F45PowerNewSkin extends FragmentActivity {

    private LinearLayout tabHolderLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private Updates updates;

    private int[][] tabs = {
            {R.string.controller, R.drawable.ic_play},
            {R.string.timeline, R.drawable.ic_timeline}
    };

    private int CURRENT_TAB = 0;

    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f45_newskin);
        updates = new Updates();
        viewPager = (ViewPager) findViewById(R.id.mainPager);
        tabHolderLayout = (LinearLayout) findViewById(R.id.tabHolder);

        createTabButtons(tabs);
        setCurrentTab(CURRENT_TAB);

        fragmentList.add(new ControllerFragment(updates));
        fragmentList.add(new TimelineFragment(updates));
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createTabButtons(int[][] tabs) {
        for (int i = 0; i < tabs.length; i++) {
            /*<Button
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:background="@drawable/btn_bg_solid_blue"
            android:drawablePadding="5dp"
            android:drawableTop="@drawable/ic_play"
            android:padding="5dp"
            android:text="CONTROLLER"
            android:textColor="@color/white"
            android:textSize="12sp" />*/
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F);
            Button button = new Button(this);
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_bg_solid_blue));
            button.setText(tabs[i][0]);
            button.setTextColor(this.getColor(R.color.white));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            button.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, tabs[i][1]), null, null);
            button.setCompoundDrawablePadding(5);
            button.setLayoutParams(params);
            if (tabHolderLayout != null) {
                tabHolderLayout.addView(button);
            }
            final String btnText = button.getText().toString();
            final int tabNdx = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updates.setState(btnText);
                    setCurrentTab(tabNdx);
                }
            });
        }
    }

    private void setCurrentTab(int tabNdx) {
        if (viewPager != null) {
            Log.d("Debug", "setting current tab " + CURRENT_TAB + " -> " + tabNdx);
            viewPager.setCurrentItem(tabNdx, true);
            CURRENT_TAB = tabNdx;
        } else {
            Log.w("Warning", "ViewPager is null!");
        }
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragmentList;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return this.fragmentList.size();
        }
    }

    public class Updates {
        private List<Observer> observerList = new ArrayList<>();
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
            notifyObservers();
        }

        public void attach(Observer observer) {
            observerList.add(observer);
        }

        public void notifyObservers() {
            for (Observer observer : observerList) {
                observer.update(this.state);
            }
        }
    }
}
