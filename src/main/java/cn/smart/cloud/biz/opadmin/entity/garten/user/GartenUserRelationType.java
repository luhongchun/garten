package cn.smart.cloud.biz.opadmin.entity.garten.user;


public enum GartenUserRelationType {
    PARENT, FATHER, MOTHER, PATERNAL_GRANDFATHER,
    PATERNAL_GRANDMOTHER, MATERNAL_GRANDFATHER, MATERNAL_GRANDMOTHER,
    SHUTTLE_PARENT, OTHER;

    public static GartenUserRelationType fromOrdinal(int ordinal) {
        final GartenUserRelationType[] types = GartenUserRelationType.values();
        for (GartenUserRelationType type : types) {
            if (ordinal == type.ordinal()) {
                return type;
            }
        }
        return PARENT;
    }
}