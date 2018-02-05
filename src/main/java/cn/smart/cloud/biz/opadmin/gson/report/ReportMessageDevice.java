package cn.smart.cloud.biz.opadmin.gson.report;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class ReportMessageDevice {
    private String sn;
    private String ip;
    private String mac;
    private boolean online;
    private Date reportTime = new Date();

    /**
     * @return the sn
     */
    public String getSn() {
        return sn;
    }

    /**
     * @param sn the sn to set
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac the mac to set
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * @return the online
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * @param online the online to set
     */
    public void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * @return the reportTime
     */
    public Date getReportTime() {
        return reportTime;
    }

    /**
     * @param reportTime the reportTime to set
     */
    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
