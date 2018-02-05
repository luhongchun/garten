package cn.smart.cloud.biz.opadmin.gson.device;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 15-8-31
 * Time: 下午9:11
 */
public class CameraConfig extends BaseEntity {

    private String cameraId;
    private int involume;
    private int outvolume;
    private int fps;
    private int bps;
    /* interval of key frame */
    private int gop;
    /* cbrmode:constant bit rate */
    private boolean cbrmode;
    private int imagegrade;
    private String moveProgrammeIds;
    private Constant.ShotSize shotSize;

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public int getInvolume() {
        return involume;
    }

    public void setInvolume(int involume) {
        this.involume = involume;
    }

    public int getOutvolume() {
        return outvolume;
    }

    public void setOutvolume(int outvolume) {
        this.outvolume = outvolume;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getBps() {
        return bps;
    }

    public void setBps(int bps) {
        this.bps = bps;
    }

    public int getGop() {
        return gop;
    }

    public void setGop(int gop) {
        this.gop = gop;
    }

    public boolean isCbrmode() {
        return cbrmode;
    }

    /* true:constant false:variable */
    public void setCbrmode(boolean cbrmode) {
        this.cbrmode = cbrmode;
    }

    public int getImagegrade() {
        return imagegrade;
    }

    /* 1~6. 1:the best */
    public void setImagegrade(int imagegrade) {
        this.imagegrade = imagegrade;
    }

    public String getMoveProgrammeIds() {
        return moveProgrammeIds;
    }

    public void setMoveProgrammeIds(String moveProgrammeIds) {
        this.moveProgrammeIds = moveProgrammeIds;
    }

    public Constant.ShotSize getShotSize() {
        return shotSize;
    }

    public void setShotSize(Constant.ShotSize shotSize) {
        this.shotSize = shotSize;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}