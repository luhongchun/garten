package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class YtIdentifyResult extends FrResult {

    private String session_id;
    private List<YtIdentityCandidate> candidates = new ArrayList<>();

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public List<YtIdentityCandidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<YtIdentityCandidate> candidates) {
        this.candidates = candidates;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
