package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class YtGroupListResult extends FrResult {

    private List<String> group_ids = new ArrayList<>();

    public List<String> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(List<String> group_ids) {
        this.group_ids = group_ids;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}