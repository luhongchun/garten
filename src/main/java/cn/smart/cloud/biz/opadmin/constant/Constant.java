package cn.smart.cloud.biz.opadmin.constant;

public class Constant {

    /**
     * ---------------------------------   for param   ---------------------------
     */
    public static final String GARTEN_ID = "gartenId";

    public static final String MEDIA_FORMAT_BMP = ".bmp";
    public static final String MEDIA_FORMAT_JPG = ".jpg";
    public static final String MEDIA_FORMAT_JPEG = ".jpeg";
    public static final String MEDIA_FORMAT_PNG = ".png";
    public static final String MEDIA_FORMAT_TS = ".ts";
    public static final String MEDIA_FORMAT_MP4 = ".mp4";

    public static final int FR_RESULT_SUC = 0;

    public static final String FFMPEG_FINISH_PRE = "other streams:";
    public static final String FFMPEG_FINISH = "Qavg";

    public enum ShotSize {
        UNKNOWN,
        DISTANT_SHOT,
        MID_SHOT,
        CLOSE_SHOT,
        CLOSEUP_SHOT
    }

    public enum PermissionType {

        VIDEO_LIVE,
        VIDEO_CLOSEUP,
        VIDEO_INOUT,
        BUS_MAP,
        SHUTTLE_INFO,
        MANAGE_CAMERA;

    }

    public enum PositionType {
        OUTSIDE, INSIDE, OUTDOOR, INDOOR, ACTIVEAREA;
    }

    public static enum CameraServiceType {
        SERVICE, SERVICE_EXPIRED
    }

    public static enum RoleType {
        CHILD, PARENT, TEACHER, HEADMASTER, BUS_TEACHER, BUS_DRIVER, BUS_MANAGER, NURSERY;

        //TODO add other type, not kindergarten. index from 100.
        public static RoleType fromOrdinal(int ordinal) {
            final RoleType[] types = RoleType.values();
            for (RoleType type : types) {
                if (ordinal == type.ordinal()) {
                    return type;
                }
            }
            return PARENT;
        }
    }
}
