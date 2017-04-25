package com.team.imagemarker.entitys;

/**
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public class HistoryModel {
    private String recordName;//记录名称
    private String operateType;//操作类型
    private String recordTime;//记录时间
    private int recordType;//记录类型(保存，已提交)

    public HistoryModel() {
    }

    public HistoryModel(String recordName, String operateType, String recordTime, int recordType) {
        this.recordName = recordName;
        this.operateType = operateType;
        this.recordTime = recordTime;
        this.recordType = recordType;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }
}
