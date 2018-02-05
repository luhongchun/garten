package cn.smart.cloud.biz.opadmin.gson;

public enum BizVideoType {

    LIVE,
    CLOSEUP,
    ATTEND,
    RECORD_EDITION,
    WONDERFUL_EDITION;

    public static BizVideoType fromOrdinal(int ordinal) {
        final BizVideoType[] types = BizVideoType.values();
        for (BizVideoType type : types) {
            if (ordinal == type.ordinal()) {
                return type;
            }
        }
        return LIVE;
    }
}