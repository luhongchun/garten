package cn.smart.cloud.biz.opadmin.gson.device;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.sql.Date;

/**
 * Author: jjj
 * Date: 15-7-16
 * Time: 下午5:59
 */
public class BadgeDetector extends Device {

    private String position;
    private String entryHint;
    private String leaveHint;
    private String entryDesc;
    private String leaveDesc;
    private Date openTime;
    private Date closeTime;
    private int entryRssi;
    private int leaveRssi;
    private int judgeCount;
    private long notifyDelayMs;
    private long notifyIntervalMs;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEntryHint() {
        return entryHint;
    }

    public void setEntryHint(String entryHint) {
        this.entryHint = entryHint;
    }

    public String getLeaveHint() {
        return leaveHint;
    }

    public void setLeaveHint(String leaveHint) {
        this.leaveHint = leaveHint;
    }

    public String getEntryDesc() {
        return entryDesc;
    }

    public void setEntryDesc(String entryDesc) {
        this.entryDesc = entryDesc;
    }

    public String getLeaveDesc() {
        return leaveDesc;
    }

    public void setLeaveDesc(String leaveDesc) {
        this.leaveDesc = leaveDesc;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public int getEntryRssi() {
        return entryRssi;
    }

    public void setEntryRssi(int entryRssi) {
        this.entryRssi = entryRssi;
    }

    public int getLeaveRssi() {
        return leaveRssi;
    }

    public void setLeaveRssi(int leaveRssi) {
        this.leaveRssi = leaveRssi;
    }

    public int getJudgeCount() {
        return judgeCount;
    }

    public void setJudgeCount(int judgeCount) {
        this.judgeCount = judgeCount;
    }

    public long getNotifyDelayMs() {
        return notifyDelayMs;
    }

    public void setNotifyDelayMs(long notifyDelayMs) {
        this.notifyDelayMs = notifyDelayMs;
    }

    public long getNotifyIntervalMs() {
        return notifyIntervalMs;
    }

    public void setNotifyIntervalMs(long notifyIntervalMs) {
        this.notifyIntervalMs = notifyIntervalMs;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
