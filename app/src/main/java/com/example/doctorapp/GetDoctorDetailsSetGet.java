package com.example.doctorapp;

public class GetDoctorDetailsSetGet {


    String scheduleday, doctorname, starttime, endtime, doctorid, scheduleid;

    public GetDoctorDetailsSetGet() {
    }

    public GetDoctorDetailsSetGet(String scheduleday, String doctorname, String starttime, String endtime, String doctorid, String scheduleid) {
        this.scheduleday = scheduleday;
        this.doctorname = doctorname;
        this.starttime = starttime;
        this.endtime = endtime;
        this.doctorid = doctorid;
        this.scheduleid = scheduleid;
    }

    public String getScheduleday() {
        return scheduleday;
    }

    public void setScheduleday(String scheduleday) {
        this.scheduleday = scheduleday;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(String scheduleid) {
        this.scheduleid = scheduleid;
    }
}
