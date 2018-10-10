package Models;

public class SuggestedTrackingModel {

    private DistNDurationModel dNd;
    private TrackingDetailModel tDM;

    public SuggestedTrackingModel(TrackingDetailModel tdm, DistNDurationModel dM) {
        this.dNd = dM;
        this.tDM = tdm;

    }


    public DistNDurationModel getdNd() {
        return dNd;
    }

    public void setdNd(DistNDurationModel dNd) {
        this.dNd = dNd;
    }

    public TrackingDetailModel gettDM() {
        return tDM;
    }

    public void settDM(TrackingDetailModel tDM) {
        this.tDM = tDM;
    }
}
