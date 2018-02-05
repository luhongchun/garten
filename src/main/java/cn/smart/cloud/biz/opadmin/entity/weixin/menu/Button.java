package cn.smart.cloud.biz.opadmin.entity.weixin.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Button {

    @Expose
    private String type;
    @Expose
    private String name;
    @Expose
    private String key;
    @Expose
    private String url;
    @Expose
    @SerializedName("sub_button")
    private List<SubButton> subButton = new ArrayList<SubButton>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<SubButton> getSubButtons() {
        return subButton;
    }

    public void setSubButtons(List<SubButton> subButtons) {
        this.subButton = subButtons;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
