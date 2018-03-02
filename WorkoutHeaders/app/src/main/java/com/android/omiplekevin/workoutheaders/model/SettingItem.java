package com.android.omiplekevin.workoutheaders.model;

import android.support.annotation.NonNull;

/**
 * Created by OMIPLEKEVIN on 019 Apr 19, 2017.
 */

public class SettingItem {

    public boolean enabled;
    public String title;
    public String description;
    public String value;
    public String action;
    public String color;

    public SettingItem(boolean enabled,
                       String title,
                       String description,
                       String value,
                       @NonNull String action) {
        this.enabled = enabled;
        this.title = title;
        this.description = description;
        this.value = value;
        this.action = action;
    }

    public SettingItem(boolean enabled,
                       String title,
                       String description,
                       String value,
                       String color,
                       @NonNull String action) {
        this.enabled = enabled;
        this.title = title;
        this.description = description;
        this.value = value;
        this.action = action;
        this.color = color;
    }
}
