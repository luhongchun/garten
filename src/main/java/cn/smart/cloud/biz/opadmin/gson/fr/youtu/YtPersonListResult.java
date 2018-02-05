package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;


public class YtPersonListResult extends FrResult {

    private List<String> person_ids;

    public List<String> getPerson_ids() {
        return person_ids;
    }

    public void setPerson_ids(List<String> person_ids) {
        this.person_ids = person_ids;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
