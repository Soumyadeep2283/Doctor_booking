package com.example.doctorapp;

public class DoctorAppoinmentScheduleSetGet {
    String timeslotid, status,slotfrom,slotto;
    public DoctorAppoinmentScheduleSetGet(){}


    public DoctorAppoinmentScheduleSetGet(String timeslotid, String status, String slotfrom, String slotto) {
        this.timeslotid = timeslotid;
        this.status = status;
        this.slotfrom = slotfrom;
        this.slotto = slotto;
    }

    public String getTimeslotid() {
        return timeslotid;
    }

    public void setTimeslotid(String timeslotid) {
        this.timeslotid = timeslotid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlotfrom() {
        return slotfrom;
    }

    public void setSlotfrom(String slotfrom) {
        this.slotfrom = slotfrom;
    }

    public String getSlotto() {
        return slotto;
    }

    public void setSlotto(String slotto) {
        this.slotto = slotto;
    }
}
