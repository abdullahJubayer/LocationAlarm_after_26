package com.example.locationalarm_21_28.ModelClass;

public class Model {

    private   String ID;
    private   String Title;
    private   String Time;
    private   String Latitude;
    private   String Longitude;
    private   String Note;
    private   String ServiceID;

    public Model() {
    }
    public Model(String ID, String title, String time, String latitude, String longitude, String note, String serviceID) {
        this.ID = ID;
        Title = title;
        Time = time;
        Latitude = latitude;
        Longitude = longitude;
        Note = note;
        ServiceID = serviceID;
    }



    public String getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public String getTime() {
        return Time;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getNote() {
        return Note;
    }

    public String getServiceID() {
        return ServiceID;
    }
}
