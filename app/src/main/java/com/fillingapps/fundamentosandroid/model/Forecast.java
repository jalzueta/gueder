package com.fillingapps.fundamentosandroid.model;

/**
 * Created by javi on 7/9/15.
 */
public class Forecast {

    private float mMaxTemp;
    private float mMinTemp;
    private float mHumidity;
    private String mDescription;
    private String mIcon;

    public Forecast(float maxTemp, float minTemp, float humidity, String description, String icon) {
        mMaxTemp = maxTemp;
        mMinTemp = minTemp;
        mHumidity = humidity;
        mDescription = description;
        mIcon = icon;
    }

    public float getMaxTemp() {
        return mMaxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        mMaxTemp = maxTemp;
    }

    public float getMinTemp() {
        return mMinTemp;
    }

    public void setMinTemp(float minTemp) {
        mMinTemp = minTemp;
    }

    public float getHumidity() {
        return mHumidity;
    }

    public void setHumidity(float humidity) {
        mHumidity = humidity;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    @Override
    public String toString() {
        return getDescription();
    }


}
