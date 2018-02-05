package cn.smart.cloud.biz.opadmin.entity;

public enum SfServiceCategory {

    SCHOOL, SHOPS;

    public static SfServiceCategory fromOrdinal(int ordinal) {
        final SfServiceCategory[] types = SfServiceCategory.values();
        for (SfServiceCategory type : types) {
            if (ordinal == type.ordinal()) {
                return type;
            }
        }
        return SCHOOL;
    }
}
