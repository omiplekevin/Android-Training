package com.omiplekevin.android.f45testbench.interfaces;

/**
 * Created by OMIPLEKEVIN on May 27, 2016.
 */
public interface OnScheduleUpdateListener {

    void onMorningListSizeChange(int newSize);

    void onAfternoonListSizeChange(int newSize);

    void onEveningListSizeChange(int newSize);

    int getMorningEntryCount();

    int getAfternoonEntryCount();

    int getEveningEntryCount();

    void doGlobalTableBalance(int balancedMorning, int balancedAfternoon, int balancedEvening);

}
