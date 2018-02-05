package cn.smart.cloud.biz.opadmin.gson.device;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class VideoParams {

    private int bps;
    private int fps;
    private int gop;
    private boolean cbrmode;
    private int imagegrade;

    /**
     * @return the bps
     */
    public int getBps() {
        return bps;
    }

    /**
     * @param bps the bps to set
     */
    public void setBps(int bps) {
        this.bps = bps;
    }

    /**
     * @return the fps
     */
    public int getFps() {
        return fps;
    }

    /**
     * @param fps the fps to set
     */
    public void setFps(int fps) {
        this.fps = fps;
    }

    /**
     * @return the gop
     */
    public int getGop() {
        return gop;
    }

    /**
     * @param gop the gop to set
     */
    public void setGop(int gop) {
        this.gop = gop;
    }

    /**
     * @return the cbrmode
     */
    public boolean isCbrmode() {
        return cbrmode;
    }

    /**
     * @param cbrmode the cbrmode to set
     */
    public void setCbrmode(boolean cbrmode) {
        this.cbrmode = cbrmode;
    }

    /**
     * @return the imagegrade
     */
    public int getImagegrade() {
        return imagegrade;
    }

    /**
     * @param imagegrade the imagegrade to set
     */
    public void setImagegrade(int imagegrade) {
        this.imagegrade = imagegrade;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}