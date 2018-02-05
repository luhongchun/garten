package cn.smart.cloud.biz.opadmin.entity.weixin;

public enum WxSubscriptionType {
    BIZ_ALL, //综合业务
    BIZ_VIDEO,
    BIZ_BUS,
    BIZ_ATTEND;

    public static WxSubscriptionType getByOrdinal(int ordinal) {
        final WxSubscriptionType[] types = values();
        for (WxSubscriptionType wxSubscriptionType : types) {
            if (wxSubscriptionType.ordinal() == ordinal) {
                return wxSubscriptionType;
            }
        }
        return BIZ_ALL;
    }
}