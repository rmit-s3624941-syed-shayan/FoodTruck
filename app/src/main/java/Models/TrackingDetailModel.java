package Models;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TrackingDetailModel {

    public Date date;
    public int trackableId;
    public int stopTime;
    public double latitude;
    public double longitude;

    public TrackingDetailModel(Date date, int trackableId, int stopTime, double latitude, double longitude) {
        this.date = date;
        this.trackableId = trackableId;
        this.stopTime = stopTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return String.format(Locale.getDefault(), "Date/Time=%s, trackableId=%d, stopTime=%d, lat=%.5f, long=%.5f", DateFormat.getDateTimeInstance(
                DateFormat.SHORT, DateFormat.MEDIUM).format(date), trackableId, stopTime, latitude, longitude);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTrackableId() {
        return trackableId;
    }


    public String getTrackableIdAsString() {
        return String.valueOf(trackableId);
    }

    public void setTrackableId(int trackableId) {
        this.trackableId = trackableId;
    }

    public int getStopTime() {
        return stopTime;
    }

    public void setStopTime(int stopTime) {
        this.stopTime = stopTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
