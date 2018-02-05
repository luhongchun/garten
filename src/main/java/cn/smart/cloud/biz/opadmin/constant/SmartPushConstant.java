package cn.smart.cloud.biz.opadmin.constant;

public class SmartPushConstant {

    public class CommandType {

        public final static String DEVICE_CONTROL = "DeviceControl";
    }

    public class CommandDeviceType {

        public final static String BOX = "box";
        public final static String APP = "app";
        public final static String CAMERA = "camera";
    }

    public class CommandActionType {

        public final static String AV = "av";
        public final static String OPERATE = "operate";
        public final static String NOTIFY = "notify";
    }

    public class CommandOperateAction {

        public final static String REBOOT = "reboot";
        public final static String RESTART = "restart";
        public final static String REFRESH = "refresh";
        public final static String RESCAN = "rescan";
    }

    public class CommandAvAction {

        public final static String FLIP = "flip";
        public final static String LIVE = "live";
        public final static String SNAPSHOT = "snapshot";
        public final static String RECORD = "record";
        public final static String inVolume = "inVolume";
        public final static String VPARAM = "videoParams";
    }

    public class CommandNotifyAction {

        public final static String USER = "user";
    }

    public class CommandNotifyActionParam {

        public final static String LIVE = "userLive";
        public final static String RECORD = "userRecord";
    }

    public class CommandParam {

        public final static String START_VIDEO = "start";
        public final static String STOP_VIDEO = "stop";

        public final static String REVERSE_VIDEO_LEFT_RIGHT = "1";
        public final static String REVERSE_VIDEO_UP_DOWN = "0";
        public final static String REVERSE_VIDEO_LEFT_RIGHT_UP_DOWN = "-1";
        public final static String REVERSE_VIDEO_RESET = "-2";
    }

}