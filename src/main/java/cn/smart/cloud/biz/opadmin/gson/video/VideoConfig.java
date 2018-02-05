package cn.smart.cloud.biz.opadmin.gson.video;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class VideoConfig {

    private String title;
    private String description;
    private String coverUrl;
    private String headerUrl;
    private Double headerDur;
    private String trailerUrl;
    private Double trailerDur;
    private boolean bgMusicOn;
    private boolean cameraAudioOn = true;
    private int sectionCount;
    private int sectionDur;
    private Date openTime;
    private Date closeTime;
    private String closeHint;
    private int aheadSeconds;
    private long videoDelayTime;
    private String faceSizeForShot;
    private boolean pushNews;
    private boolean live; //实时直播
    private boolean history;

    private String kinderGartenId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public Double getHeaderDur() {
        return headerDur;
    }

    public void setHeaderDur(Double headerDur) {
        this.headerDur = headerDur;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public Double getTrailerDur() {
        return trailerDur;
    }

    public void setTrailerDur(Double trailerDur) {
        this.trailerDur = trailerDur;
    }

    public boolean isBgMusicOn() {
        return bgMusicOn;
    }

    public void setBgMusicOn(boolean bgMusicOn) {
        this.bgMusicOn = bgMusicOn;
    }

    public boolean isCameraAudioOn() {
        return cameraAudioOn;
    }

    public void setCameraAudioOn(boolean cameraAudioOn) {
        this.cameraAudioOn = cameraAudioOn;
    }

    public int getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(int sectionCount) {
        this.sectionCount = sectionCount;
    }

    public int getSectionDur() {
        return sectionDur;
    }

    public void setSectionDur(int sectionDur) {
        this.sectionDur = sectionDur;
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

    public String getCloseHint() {
        return closeHint;
    }

    public void setCloseHint(String closeHint) {
        this.closeHint = closeHint;
    }

    public int getAheadSeconds() {
        return aheadSeconds;
    }

    public void setAheadSeconds(int aheadSeconds) {
        this.aheadSeconds = aheadSeconds;
    }

    public long getVideoDelayTime() {
        return videoDelayTime;
    }

    public void setVideoDelayTime(long videoDelayTime) {
        this.videoDelayTime = videoDelayTime;
    }

    public String getFaceSizeForShot() {
        return faceSizeForShot;
    }

    public void setFaceSizeForShot(String faceSizeForShot) {
        this.faceSizeForShot = faceSizeForShot;
    }

    public String getKinderGartenId() {
        return kinderGartenId;
    }

    public void setKinderGartenId(String kinderGartenId) {
        this.kinderGartenId = kinderGartenId;
    }

    public boolean isPushNews() {
        return pushNews;
    }

    public void setPushNews(boolean pushNews) {
        this.pushNews = pushNews;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}