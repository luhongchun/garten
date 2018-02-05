package cn.smart.cloud.biz.opadmin.gson.report;

import cn.smart.cloud.biz.opadmin.entity.BaseMongoEntity;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class ReportMessage extends BaseMongoEntity {

    @SerializedName("boxInfo")
    private ReportMessageBox boxMessage;
    @SerializedName("commandInfos")
    private List<ReportMessageCommand> commandMessageList;
    @SerializedName("cameraInfos")
    private List<ReportMessageCamera> cameraMessageList;

    public ReportMessageBox getBoxMessage() {
        return boxMessage;
    }

    public void setBoxMessage(ReportMessageBox boxMessage) {
        this.boxMessage = boxMessage;
    }

    public List<ReportMessageCommand> getCommandMessageList() {
        return commandMessageList;
    }

    public void setCommandMessageList(
            List<ReportMessageCommand> commandMessageList) {
        this.commandMessageList = commandMessageList;
    }

    public List<ReportMessageCamera> getCameraMessageList() {
        return cameraMessageList;
    }

    public void setCameraMessageList(List<ReportMessageCamera> cameraMessageList) {
        this.cameraMessageList = cameraMessageList;
    }

    public List<ReportMessageCamera> getOfflineCameraMessageList() {
        List<ReportMessageCamera> cameraList = new ArrayList<ReportMessageCamera>();
        for (ReportMessageCamera msg : cameraMessageList) {
            if (!msg.isOnline()) {
                cameraList.add(msg);
            }
        }
        return cameraList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}