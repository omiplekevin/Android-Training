package com.android.omiplekevin.workoutheaders.model.stationavatar;

/**
 * Created by Philip on 11/24/2017.
 */

public class AvatarDataModel {

    private int actualAge;
    private double actualWeight;
    private long actualTimeStamp;
    private String avatar;
    private int batteryVoltage;
    private int bpm;
    private int bpms;
    private int bpmsCount;
    private double calories;
    private int certainty;
    private boolean fake;
    private long firstSeen;
    private boolean guest;
    private int id;
    private int max;
    private String name;
    private boolean newUser;
    private long oldTimeStamp;
    private int percent;
    private int percentile;
    private int percentileTotal;
    private double points;
    private  int pulsed;
    private long serial;
    private String sex;
    private long superserial;
    private long timeStamp;

    public AvatarDataModel(int actualAge, double actualWeight, long actualTimeStamp, String avatar, int batteryVoltage, int bpm, int bpms, int bpmsCount, double calories, int certainty, boolean fake, long firstSeen, boolean guest, int id, int max, String name, boolean newUser, long oldTimeStamp, int percent, int percentile, int percentileTotal, double points, int pulsed, long serial, String sex, long superserial, long timeStamp) {
        this.actualAge = actualAge;
        this.actualWeight = actualWeight;
        this.actualTimeStamp = actualTimeStamp;
        this.avatar = avatar;
        this.batteryVoltage = batteryVoltage;
        this.bpm = bpm;
        this.bpms = bpms;
        this.bpmsCount = bpmsCount;
        this.calories = calories;
        this.certainty = certainty;
        this.fake = fake;
        this.firstSeen = firstSeen;
        this.guest = guest;
        this.id = id;
        this.max = max;
        this.name = name;
        this.newUser = newUser;
        this.oldTimeStamp = oldTimeStamp;
        this.percent = percent;
        this.percentile = percentile;
        this.percentileTotal = percentileTotal;
        this.points = points;
        this.pulsed = pulsed;
        this.serial = serial;
        this.sex = sex;
        this.superserial = superserial;
        this.timeStamp = timeStamp;
    }

    public AvatarDataModel() {
    }

    public int getActualAge() {
        return actualAge;
    }

    public void setActualAge(int actualAge) {
        this.actualAge = actualAge;
    }

    public double getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(double actualWeight) {
        this.actualWeight = actualWeight;
    }

    public long getActualTimeStamp() {
        return actualTimeStamp;
    }

    public void setActualTimeStamp(long actualTimeStamp) {
        this.actualTimeStamp = actualTimeStamp;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(int batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public int getBpms() {
        return bpms;
    }

    public void setBpms(int bpms) {
        this.bpms = bpms;
    }

    public int getBpmsCount() {
        return bpmsCount;
    }

    public void setBpmsCount(int bpmsCount) {
        this.bpmsCount = bpmsCount;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public int getCertainty() {
        return certainty;
    }

    public void setCertainty(int certainty) {
        this.certainty = certainty;
    }

    public boolean isFake() {
        return fake;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }

    public long getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(long firstSeen) {
        this.firstSeen = firstSeen;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public long getOldTimeStamp() {
        return oldTimeStamp;
    }

    public void setOldTimeStamp(long oldTimeStamp) {
        this.oldTimeStamp = oldTimeStamp;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getPercentile() {
        return percentile;
    }

    public void setPercentile(int percentile) {
        this.percentile = percentile;
    }

    public int getPercentileTotal() {
        return percentileTotal;
    }

    public void setPercentileTotal(int percentileTotal) {
        this.percentileTotal = percentileTotal;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public int getPulsed() {
        return pulsed;
    }

    public void setPulsed(int pulsed) {
        this.pulsed = pulsed;
    }

    public long getSerial() {
        return serial;
    }

    public void setSerial(long serial) {
        this.serial = serial;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getSuperserial() {
        return superserial;
    }

    public void setSuperserial(long superserial) {
        this.superserial = superserial;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
