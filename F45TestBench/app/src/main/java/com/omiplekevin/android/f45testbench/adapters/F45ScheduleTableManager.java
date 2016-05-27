package com.omiplekevin.android.f45testbench.adapters;

import android.util.Log;

import com.google.gson.internal.LinkedTreeMap;
import com.omiplekevin.android.f45testbench.uicomponent.F45ScheduleStrip;

import java.util.Map;
import java.util.Random;

/**
 * Created by OMIPLEKEVIN on May 27, 2016.
 */
public class F45ScheduleTableManager {

    private Map<String, F45ScheduleStrip> subscribers;

    private int MAX_MORNING_COUNT = 0;
    private int MAX_AFTERNOON_COUNT = 0;
    private int MAX_EVENING_COUNT = 0;

    public F45ScheduleTableManager(){
        subscribers = new LinkedTreeMap<>();
    }

    public void attachSubscriber(String key, F45ScheduleStrip f45ScheduleStrip) {
        subscribers.put(key, f45ScheduleStrip);
    }

    public void notifySubscribers(String day, String section) {


        switch (section) {
            case "morning":
                int tempMorningMax = 0;
                for (Map.Entry<String, F45ScheduleStrip> f45ScheduleStrip : subscribers.entrySet()) {
                    if (day.equals(f45ScheduleStrip.getKey())) {
                        Log.e("Manager", "changing @ " + day);
                        f45ScheduleStrip.getValue().onMorningListSizeChange(new Random().nextInt(5));
                    }

                    //get max mornings
                    int morningCount = f45ScheduleStrip.getValue().morningDataCount;
                    if (morningCount > tempMorningMax) {
                        tempMorningMax = morningCount;
                    }
                }
                //update global max data count...
                MAX_MORNING_COUNT = tempMorningMax;
                break;
            case "afternoon":
                int tempAfternoonMax = 0;
                for (Map.Entry<String, F45ScheduleStrip> f45ScheduleStrip : subscribers.entrySet()) {
                    if (day.equals(f45ScheduleStrip.getKey())) {
                        Log.e("Manager", "changing @ " + day);
                        f45ScheduleStrip.getValue().onAfternoonListSizeChange(new Random().nextInt(5));
                    }

                    //get max afternoons
                    int afternoonCount = f45ScheduleStrip.getValue().afternoonDataCount;
                    if (afternoonCount > tempAfternoonMax) {
                        tempAfternoonMax = afternoonCount;
                    }
                }
                //update global max data count...
                MAX_AFTERNOON_COUNT = tempAfternoonMax;
                break;
            case "evening":
                int tempEveningMax = 0;
                for (Map.Entry<String, F45ScheduleStrip> f45ScheduleStrip : subscribers.entrySet()) {
                    if (day.equals(f45ScheduleStrip.getKey())) {
                        Log.e("Manager", "changing @ " + day);
                        f45ScheduleStrip.getValue().onEveningListSizeChange(new Random().nextInt(5));
                    }

                    //get max evenings
                    int eveningCount = f45ScheduleStrip.getValue().eveningDataCount;
                    if (eveningCount > tempEveningMax) {
                        tempEveningMax = eveningCount;
                    }
                }
                //update global max data count...
                MAX_EVENING_COUNT = tempEveningMax;
                break;
        }
        Log.e("Manager", MAX_MORNING_COUNT + " " + MAX_AFTERNOON_COUNT + " " + MAX_EVENING_COUNT);
    }

    public boolean balanceTables() {
        try {
            for (Map.Entry<String, F45ScheduleStrip> f45ScheduleStrip : subscribers.entrySet()) {
//                Log.e("Manager", "balancing @ " + f45ScheduleStrip.getKey() + " with " + MAX_MORNING_COUNT + " " + MAX_AFTERNOON_COUNT + " " + MAX_EVENING_COUNT);
                f45ScheduleStrip.getValue().doGlobalTableBalance(MAX_MORNING_COUNT, MAX_AFTERNOON_COUNT, MAX_EVENING_COUNT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
