package cn.smart.cloud.biz.opadmin.entity.weixin.material;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 16-1-11
 * Time: 下午10:22
 */
public class WxMaterialRequest {

    private String type;
    private int offset;
    private int count;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
