package cn.smart.cloud.biz.opadmin.gson.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReportMessageTable {
    private static ReportMessageTable reportMessageTable;

    private Map<String, ReportMessage> reportMessageMap = new ConcurrentHashMap<String, ReportMessage>();

    private ReportMessageTable() {

    }

    public static ReportMessageTable getInstance() {
        if (reportMessageTable == null) {
            reportMessageTable = new ReportMessageTable();
        }
        return reportMessageTable;
    }

    public void setReportMessage(String deviceId, ReportMessage reportInfo) {
        reportMessageMap.put(deviceId, reportInfo);
    }

    public ReportMessage getReportMessage(String deviceId) {
        return reportMessageMap.get(deviceId);
    }

    public ReportMessage removeReportMessage(String deviceId) {
        return reportMessageMap.remove(deviceId);
    }

    public List<ReportMessage> getAllReportMessage() {
        List<ReportMessage> deviceReportMsgList = new ArrayList<ReportMessage>();
        for (String key : reportMessageMap.keySet()) {
            deviceReportMsgList.add(reportMessageMap.get(key));
        }
        return deviceReportMsgList;
    }
}
