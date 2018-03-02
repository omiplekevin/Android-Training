package com.android.omiplekevin.workoutheaders.model;

/**
 * Created by Philipp on 10/10/2017.
 */

public class TVStatusItem {
    public boolean isActive;
    public String name;
    public String ip;

    public TVStatusItem(boolean isActive, String name, String ip) {
        this.isActive = isActive;
        this.name = name;
        this.ip = ip;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
