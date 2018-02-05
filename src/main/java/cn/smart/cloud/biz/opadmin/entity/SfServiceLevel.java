package cn.smart.cloud.biz.opadmin.entity;

public enum SfServiceLevel {

    BASE, VAT;

    public static SfServiceLevel fromOrdinal(int ordinal) {
        final SfServiceLevel[] types = SfServiceLevel.values();
        for (SfServiceLevel type : types) {
            if (ordinal == type.ordinal()) {
                return type;
            }
        }
        return BASE;
    }
}
