package cn.smart.cloud.biz.opadmin.gson.report;

import java.util.List;

public class ReportMessagesBean {
    private List<ReportMessage> reportMessageList;

    /**
     * @return the reportMessageList
     */
    public List<ReportMessage> getReportMessageList() {
        return reportMessageList;
    }

    /**
     * @param reportMessageList the reportMessageList to set
     */
    public void setReportMessageList(List<ReportMessage> reportMessageList) {
        this.reportMessageList = reportMessageList;
    }
}
