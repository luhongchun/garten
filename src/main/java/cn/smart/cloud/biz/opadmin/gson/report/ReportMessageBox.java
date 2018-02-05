package cn.smart.cloud.biz.opadmin.gson.report;

import java.util.Date;

public class ReportMessageBox extends ReportMessageDevice {

    private double delayR;
    private double delayS;
    private String boxId;
    private String name;
    private String cpuUsage;
    private String memFree;
    private double uploadRate;
    private double recordRateOneCam;
    private String versionCode;
    private Date upgradeTime;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the boxId
     */
    public String getBoxId() {
        return boxId;
    }

    /**
     * @param boxId the boxId to set
     */
    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    /**
     * @return the delayR
     */
    public double getDelayR() {
        return delayR;
    }

    /**
     * @param delayR the delayR to set
     */
    public void setDelayR(double delayR) {
        this.delayR = delayR;
    }

    /**
     * @return the delayS
     */
    public double getDelayS() {
        return delayS;
    }

    /**
     * @param delayS the delayS to set
     */
    public void setDelayS(double delayS) {
        this.delayS = delayS;
    }

    /**
     * @return the cpuUsage
     */
    public String getCpuUsage() {
        return cpuUsage;
    }

    /**
     * @param cpuUsage the cpuUsage to set
     */
    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    /**
     * @return the memFree
     */
    public String getMemFree() {
        return memFree;
    }

    /**
     * @param memFree the memFree to set
     */
    public void setMemFree(String memFree) {
        this.memFree = memFree;
    }

    /**
     * @return the uploadRate
     */
    public double getUploadRate() {
        return uploadRate;
    }

    /**
     * @param uploadRate the uploadRate to set
     */
    public void setUploadRate(double uploadRate) {
        this.uploadRate = uploadRate;
    }

    public double getRecordRateOneCam() {
        return recordRateOneCam;
    }

    public void setRecordRateOneCam(double recordRateOneCam) {
        this.recordRateOneCam = recordRateOneCam;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Date getUpgradeTime() {
        return upgradeTime;
    }

    public void setUpgradeTime(Date upgradeTime) {
        this.upgradeTime = upgradeTime;
    }

}
