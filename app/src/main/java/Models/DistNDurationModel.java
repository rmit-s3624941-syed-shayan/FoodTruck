package Models;

public class DistNDurationModel {
    private String distanceTxt;
    private String durationTxt;
    private int durationVal;
    private int distanceVal;

    public DistNDurationModel(String distanceTxt, String durationTxt, int durationVal, int distanceVal) {
        this.distanceTxt = distanceTxt;
        this.durationTxt = durationTxt;
        this.durationVal = durationVal;
        this.distanceVal = distanceVal;
    }

    public void printdata()
    {
        System.out.println("Distance: " + this.distanceTxt + " - " + this.distanceVal);

        System.out.println("Duration: " + this.durationTxt + " - " + this.durationVal);
    }

    public int getDurationVal() {
        return durationVal;
    }

    public void setDurationVal(int durationVal) {
        this.durationVal = durationVal;
    }

    public String getDurationTxt() {
        return durationTxt;
    }

    public void setDurationTxt(String durationTxt) {
        this.durationTxt = durationTxt;
    }

    public String getDistanceTxt() {
        return distanceTxt;
    }

    public void setDistanceTxt(String distanceTxt) {
        this.distanceTxt = distanceTxt;
    }

    public int getDistanceVal() {
        return distanceVal;
    }

    public void setDistanceVal(int distanceVal) {
        this.distanceVal = distanceVal;
    }
}
