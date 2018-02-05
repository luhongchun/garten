package cn.smart.cloud.biz.opadmin.entity.weixin;

public enum WxSubscriptionMode {
    DEV_MODE, EDIT_MODE;

    public static WxSubscriptionMode getByOrdinal(int ordinal) {
        final WxSubscriptionMode[] modes = values();
        for (WxSubscriptionMode wxSubscriptionMode : modes) {
            if (wxSubscriptionMode.ordinal() == ordinal) {
                return wxSubscriptionMode;
            }
        }
        return DEV_MODE;
    }
}
