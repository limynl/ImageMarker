package com.team.imagemarker.entitys.task;


public class TimeLineModel {

    private String mMessage;
    private String mDate;
    private String mStatus;

    public TimeLineModel() {
    }

    public TimeLineModel(String mMessage, String mDate, String mStatus) {
        this.mMessage = mMessage;
        this.mDate = mDate;
        this.mStatus = mStatus;
    }

    public String getMessage() {
        return mMessage;
    }

    public void semMessage(String message) {
        this.mMessage = message;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}
