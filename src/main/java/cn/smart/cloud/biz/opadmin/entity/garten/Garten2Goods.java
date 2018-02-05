package cn.smart.cloud.biz.opadmin.entity.garten;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Garten2Goods extends BaseEntity {

    private String gartenId;
    private String goodsId;

    public String getGartenId() {
        return gartenId;
    }

    public void setGartenId(String gartenId) {
        this.gartenId = gartenId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}