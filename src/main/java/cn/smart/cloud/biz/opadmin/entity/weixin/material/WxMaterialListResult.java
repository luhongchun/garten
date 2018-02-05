package cn.smart.cloud.biz.opadmin.entity.weixin.material;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Author: jjj
 * Date: 16-1-11
 * Time: 下午10:18
 */
public class WxMaterialListResult {

    private int total_count;
    private int item_count;
    private List<WxMaterialInfo> item;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public List<WxMaterialInfo> getItem() {
        return item;
    }

    public void setItem(List<WxMaterialInfo> item) {
        this.item = item;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
