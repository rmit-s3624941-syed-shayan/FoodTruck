package Models;

import java.util.concurrent.atomic.AtomicInteger;

public class SelectedTrackingModel{
    private static final AtomicInteger count = new AtomicInteger(0);


    TrackableDataModel trackable;
    TrackingDetailModel trackingDetail;
    private final int selectionId;
    private String meetupTime;


    public SelectedTrackingModel(TrackableDataModel trackable, TrackingDetailModel trackingDetail) {
        this.trackable = trackable;
        this.trackingDetail = trackingDetail;
        selectionId = count.incrementAndGet();
        this.meetupTime = "";
    }

    public SelectedTrackingModel(TrackableDataModel trackable, TrackingDetailModel trackingDetail, int sId) {
        this.trackable = trackable;
        this.trackingDetail = trackingDetail;
        selectionId = sId;
        this.meetupTime = "";
    }
    
    public TrackableDataModel getTrackable() {
        return trackable;
    }

    public void setTrackable(TrackableDataModel trackable) {
        this.trackable = trackable;
    }

    public TrackingDetailModel getTrackingDetail() {
        return trackingDetail;
    }

    public void setTrackingDetail(TrackingDetailModel trackingDetail) {
        this.trackingDetail = trackingDetail;
    }
    public String getMeetupTime() {
        return meetupTime;
    }

    public void setMeetupTime(String meetupTime) {
        this.meetupTime = meetupTime;
    }

    public int getSelectionId() {
        return selectionId;
    }

}
