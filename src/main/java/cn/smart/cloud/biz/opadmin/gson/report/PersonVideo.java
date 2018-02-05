package cn.smart.cloud.biz.opadmin.gson.report;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PersonVideo {

    private String id;
    private int cnt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}