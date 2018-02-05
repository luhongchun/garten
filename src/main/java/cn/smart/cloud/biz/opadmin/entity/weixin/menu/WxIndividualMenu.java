package cn.smart.cloud.biz.opadmin.entity.weixin.menu;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jjj
 * Date: 16-1-8
 * Time: 上午1:36
 */
public class WxIndividualMenu {

    private List<Button> button = new ArrayList<Button>();
    private Matchrule matchrule;
    private String menuid;

    public List<Button> getButton() {
        return button;
    }

    public void setButton(List<Button> button) {
        this.button = button;
    }

    public Matchrule getMatchrule() {
        return matchrule;
    }

    public void setMatchrule(Matchrule matchrule) {
        this.matchrule = matchrule;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
