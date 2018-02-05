package cn.smart.cloud.biz.opadmin.constant;

import java.util.Map;
import java.util.TreeMap;

public class SmartCodeMsgConstant {

    public static final String SMART_ACTION_RESULT_CODE = "code";
    public static final String SMART_ACTION_RESULT_MESSAGE = "msg";

    public final static int SMART_ERROR_CODE_SUCCESS = 0;//请求成功
    public final static int SMART_ERROR_CODE_SYSTEM_BUSY = -1;//系统繁忙
    public final static int SMART_ERROR_CODE_INVALID_PARAM = 10001;//invalid parameter
    public final static int SMART_ERROR_CODE_INVALID_DATE = 10002;
    public final static int SMART_ERROR_CODE_INVALID_ACCESS_TOKEN = 30001;//非法的access_token
    public final static int SMART_ERROR_CODE_GET_ACCESS_TOKEN_ERROR = 30002;//获取access_token时app_id或者app_secret错误
    public final static int SMART_ERROR_CODE_INVALID_GRANT_TYPE = 30003;//不合法的grant_type
    public final static int SMART_ERROR_CODE_INVALID_APP_ID = 30004;//invalid app_id
    public final static int SMART_ERROR_CODE_UNKNOWN_DEVICE_ID = 40011;//设备ID不存在
    public final static int SMART_ERROR_CODE_UNKNOWN_CAMERA_ID = 40012;//摄像头ID不存在
    public final static int SMART_ERROR_CODE_UNKNOWN_VIDEO_ID = 40013;//摄像头的输出视频ID不存在
    public final static int SMART_ERROR_CODE_SOURCE_EXIST = 40014;//指定视频输入入已经添加到系统当中了,无无需再次添加
    public final static int SMART_ERROR_CODE_INVALID_SOURCE_ID = 40021;//video_source_id不合法
    public final static int SMART_ERROR_CODE_INVALID_EXPIRES_PARAM = 40022;//expires_in时间不合法
    public final static int SMART_ERROR_CODE_INVALID_RESTART_PARAM = 40023;//restart参数不合法
    public final static int SMART_ERROR_CODE_SOURCE_OFF_LINE = 40024;//指定视频源处于离线状态
    public final static int SMART_ERROR_CODE_INVALID_SOURCE_ID_LIST = 40025;//视频源列表不合法
    public final static int SMART_ERROR_CODE_APP_NOT_AUTH = 40060;//应用程序未授权

    public final static int SMART_ERROR_CODE_MAC_IS_REGISTERED = 50001;//MAC 地址已注册
    public final static int SMART_ERROR_CODE_DEVICE_TYPE_ERROR = 50002;//设备类型错误
    public final static int SMART_ERROR_CODE_PARAMS_ERROR = 50011;//输入参数错误或者加密密钥错误
    public final static int SMART_ERROR_CODE_SN_MAC_INCONSISTENCY = 50012;//序列号与mac地址不一致
    public final static int SMART_ERROR_CODE_UNKNOWN_DEVICE_SN = 50013;
    public final static int SMART_ERROR_CODE_INVALID_DEVICE_ACCESS_TOKEN = 50014;

    public final static int SMART_ERROR_CODE_INVALID_GARTEN = 60001;
    public final static int SMART_ERROR_CODE_INVALID_GARTEN_CLASS = 60002;
    public final static int SMART_ERROR_CODE_INVALID_GARTEN_USER = 60003;
    public final static int SMART_ERROR_CODE_INVALID_GARTEN_VIDEO = 60004;
    public final static int SMART_ERROR_CODE_INVALID_GARTEN_VIDEO_TYPE = 60005;

    public final static String SMART_ERROR_MSG_SUCCESS = "success";

    private static final Map<Integer, String> errorCodeMessageMap = new TreeMap<Integer, String>();

    static {
        errorCodeMessageMap.put(SMART_ERROR_CODE_SUCCESS, SMART_ERROR_MSG_SUCCESS);
        errorCodeMessageMap.put(SMART_ERROR_CODE_SYSTEM_BUSY, "system busy");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_PARAM, "invalid parameter");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_DATE, "invalid date");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_ACCESS_TOKEN, "invalid access token");
        errorCodeMessageMap.put(SMART_ERROR_CODE_GET_ACCESS_TOKEN_ERROR, "error app_id or app_secret in get access_token");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_GRANT_TYPE, "invalid grant_type");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_APP_ID, "invalid app_id");
        errorCodeMessageMap.put(SMART_ERROR_CODE_UNKNOWN_DEVICE_ID, "unknown device_id");
        errorCodeMessageMap.put(SMART_ERROR_CODE_UNKNOWN_CAMERA_ID, "unknown camera_id");
        errorCodeMessageMap.put(SMART_ERROR_CODE_UNKNOWN_VIDEO_ID, "unknown video_id");
        errorCodeMessageMap.put(SMART_ERROR_CODE_SOURCE_EXIST, "video source is exist");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_SOURCE_ID, "invalid source_id param");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_EXPIRES_PARAM, "invalid expires param");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_RESTART_PARAM, "invalid restart param");
        errorCodeMessageMap.put(SMART_ERROR_CODE_SOURCE_OFF_LINE, "video source off line");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_SOURCE_ID_LIST, "invalid source_id_list");
        errorCodeMessageMap.put(SMART_ERROR_CODE_APP_NOT_AUTH, "app not auth");
        errorCodeMessageMap.put(SMART_ERROR_CODE_MAC_IS_REGISTERED, "mac address is registered");
        errorCodeMessageMap.put(SMART_ERROR_CODE_DEVICE_TYPE_ERROR, "device type error");
        errorCodeMessageMap.put(SMART_ERROR_CODE_PARAMS_ERROR, "input param error");
        errorCodeMessageMap.put(SMART_ERROR_CODE_SN_MAC_INCONSISTENCY, "sn is not consist for mac");
        errorCodeMessageMap.put(SMART_ERROR_CODE_UNKNOWN_DEVICE_SN, "unknown device sn");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_DEVICE_ACCESS_TOKEN, "invalid device access token");

        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_GARTEN, "invalid garten");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_GARTEN_CLASS, "invalid class");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_GARTEN_USER, "invalid user");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_GARTEN_VIDEO, "invalid video");
        errorCodeMessageMap.put(SMART_ERROR_CODE_INVALID_GARTEN_VIDEO_TYPE, "invalid video type");
    }

    public static Map<String, Object> createResultMapError(int errorCode) {
        Map<String, Object> result = new TreeMap<String, Object>();

        result.put(SMART_ACTION_RESULT_CODE, errorCode);
        result.put(SMART_ACTION_RESULT_MESSAGE, errorCodeMessageMap.get(errorCode));

        return result;
    }

    public static Map<String, Object> createResultMapSuccess(String message) {
        Map<String, Object> result = new TreeMap<String, Object>();

        result.put(SMART_ACTION_RESULT_CODE, SMART_ERROR_CODE_SUCCESS);
        result.put(SMART_ACTION_RESULT_MESSAGE, message);

        return result;
    }
}