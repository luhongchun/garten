package cn.smart.cloud.biz.opadmin.gson.video;

public class M3u8Desc {

    /* in seconds with 0.000 format */
    private double duration;
    private String url;

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
