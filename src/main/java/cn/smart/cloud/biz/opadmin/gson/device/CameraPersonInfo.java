package cn.smart.cloud.biz.opadmin.gson.device;

import java.util.Date;
import java.util.List;

public class CameraPersonInfo {

    private Date recordDate;
    private List<CameraPerson> cameraPersons;

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public List<CameraPerson> getCameraPersons() {
        return cameraPersons;
    }

    public void setCameraPersons(List<CameraPerson> cameraPersons) {
        this.cameraPersons = cameraPersons;
    }
}
