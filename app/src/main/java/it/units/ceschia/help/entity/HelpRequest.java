package it.units.ceschia.help.entity;


import com.google.firebase.Timestamp;

public class HelpRequest {
    private String userId;
    private User user;
    private double latitude;
    private double longitude;
    private double altitude;
    private HelpRequestType type;
    private Timestamp time;
    private boolean in_charge;

    public HelpRequest() {
    }

    public HelpRequest(String id, User user, Position pos, HelpRequestType type, Timestamp time,boolean in_charge) {
        this.userId = id;
        this.user=user;
        this.latitude = pos.getLatitude();
        this.longitude = pos.getLongitude();
        this.altitude=pos.getAltitude();
        this.type=type;
        this.time=time;
        this.in_charge =in_charge;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public HelpRequestType getType() {
        return type;
    }

    public void setType(HelpRequestType type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isIn_charge() {
        return in_charge;
    }

    public void setIn_charge(boolean in_charge) {
        this.in_charge = in_charge;
    }

    @Override
    public String toString() {
        return "HelpRequest{" +
                "userId='" + userId + '\'' +
                ", user=" + user +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", type=" + type +
                ", time=" + time +
                ", is_in_charge=" + in_charge +
                '}';
    }
}
