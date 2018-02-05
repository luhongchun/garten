package cn.smart.cloud.biz.opadmin.gson.report;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ReportMessageCommand {
    private String cmdName;
    private int cntSumPush;
    private int cntSumLoop;
    private int cntSumSuc;

    /**
     * @return the cmdName
     */
    public String getCmdName() {
        return cmdName;
    }

    /**
     * @param cmdName the cmdName to set
     */
    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    /**
     * @return the cntSumPush
     */
    public int getCntSumPush() {
        return cntSumPush;
    }

    /**
     * @param cntSumPush the cntSumPush to set
     */
    public void setCntSumPush(int cntSumPush) {
        this.cntSumPush = cntSumPush;
    }

    /**
     * @return the cntSumLoop
     */
    public int getCntSumLoop() {
        return cntSumLoop;
    }

    /**
     * @param cntSumLoop the cntSumLoop to set
     */
    public void setCntSumLoop(int cntSumLoop) {
        this.cntSumLoop = cntSumLoop;
    }

    /**
     * @return the cntSumSuc
     */
    public int getCntSumSuc() {
        return cntSumSuc;
    }

    /**
     * @param cntSumSuc the cntSumSuc to set
     */
    public void setCntSumSuc(int cntSumSuc) {
        this.cntSumSuc = cntSumSuc;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
