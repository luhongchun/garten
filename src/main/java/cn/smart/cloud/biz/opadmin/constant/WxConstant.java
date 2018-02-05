package cn.smart.cloud.biz.opadmin.constant;

public class WxConstant {


    /**
     * ---------------------------------   for param   ---------------------------
     */
    public static final String WX_AUTH_INSTANCE_ID = "instanceId";
    public static final String WX_AUTH_TIME_STAMP = "timestamp";
    public static final String WX_AUTH_NONCE = "nonce";
    public static final String WX_AUTH_SIGNATURE = "signature";
    public static final String WX_AUTH_ECHO_STR = "echostr";

    public static final String WX_USER_ID = "user_id";
    public static final String WX_OPEN_ID = "openId";
    public static final String WX_APP_ID = "app_id";
    public static final String WX_SECRET_ID = "secret_id";
    public static final String WX_SCENE_ID = "scene_id";
    public static final String WX_OFFICIAL_ACCOUNT_ID = "official_account_id";
    public static final String WX_SUBSCRIPTION_ID = "subscription_id";

    /**
     * ---------------------------------   for qrcode   ---------------------------
     */
    public static final int TEMP_QRCODE_EXPIRE_SECONDS = 60 * 30;


    /**
     * ---------------------------------   for message   --------------------------
     */
    public static final String MSG_TYPE_TEXT = "text";
    public static final String MSG_TYPE_IMAGE = "image";
    public static final String MSG_TYPE_VOICE = "voice";
    public static final String MSG_TYPE_VIDEO = "video";
    public static final String MSG_TYPE_LOCATION = "location";
    public static final String MSG_TYPE_LINK = "link";
    public static final String MSG_TYPE_EVENT = "event";
    public static final String MSG_TYPE_NEWS = "news";
    public static final String MSG_TYPE_TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";
    public static final String MSG_TYPE_UNKNOWN = "unknown";

    /**
     * ---------------------------------   for event   ----------------------------
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";
    public static final String EVENT_TYPE_SCAN = "SCAN";
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    public static final String EVENT_DEFAULT_SNAPSHOT = "EVENT_KEY_TAKE_PHOTO";
    public static final String EVENT_DEFAULT_RECORD_VIDEO = "EVENT_KEY_RECORD_VIDEO";

    /**
     * ---------------------------------   for menu   -----------------------------
     */
    public static final String CUSTOM_MENU_TYPE_CLICK = "click";
    public static final String CUSTOM_MENU_TYPE_VIEW = "view";

    /**
     * ---------------------------------   for request result code   --------------
     */
    public static final int WX_SYSTEM_BUSY = -1;
    public static final int WX_SUCCESS = 0;
    public static final int WX_INVALID_CREDENTIAL = 40001;
    public static final int WX_INVALID_CREDENTIAL_TYPE = 40002;
    public static final int WX_INVALID_OPENID = 40003;
    public static final int WX_INVALID_MEDIA_FILE_TYPE = 40004;
    public static final int WX_INVALID_FILE_TYPE = 40005;
    public static final int WX_INVALID_FILE_SIZE = 40006;
    public static final int WX_INVALID_MEDIA_FILE_ID = 40007;
    public static final int WX_INVALID_MESSAGE_TYPE = 40008;
    public static final int WX_INVALID_IMAGE_FILE_SIZE = 40009;
    public static final int WX_INVALID_VOICE_FILE_SIZE = 40010;
    public static final int WX_INVALID_VIDEO_FILE_SIZE = 40011;
    public static final int WX_INVALID_COVER_IMAGE_FILE_SIZE = 40012;
    public static final int WX_INVALID_APPID = 40013;
    public static final int WX_INVALID_ACCESS = 40014;
    public static final int WX_INVALID_MENU_TYPE = 40015;
    public static final int WX_INVALID_BUTTON_SIZE = 40016;
    public static final int WX_INVALID_BUTTON_SIZE_1 = 40017;
    public static final int WX_INVALID_BUTTON_NAME_LENGTH = 40018;
    public static final int WX_INVALID_BUTTON_KEY_LENGTH = 40019;
    public static final int WX_INVALID_BUTTON_URL_LENGTH = 40020;
    public static final int WX_INVALID_MENU_VERSION = 40021;
    public static final int WX_INVALID_SUB_MENU_LEVEL = 40022;
    public static final int WX_INVALID_SUB_MENU_BUTTON_SIZE = 40023;
    public static final int WX_INVALID_SUB_MENU_BUTTON_TYPE = 40024;
    public static final int WX_INVALID_SUB_MENU_BUTTON_NAME_LENGTH = 40025;
    public static final int WX_INVALID_SUB_MENU_BUTTON_KEY_LENGTH = 40026;
    public static final int WX_INVALID_SUB_MENU_BUTTON_URL_LENGTH = 40027;
    public static final int WX_INVALID_CUSTOMER_MENU_USER = 40028;
    public static final int WX_LACK_ACCESS_TOKEN = 41001;
    public static final int WX_LACK_APPID = 41002;
    public static final int WX_LACK_REFRESH_TOKEN = 41003;
    public static final int WX_LACK_SECRET = 41004;
    public static final int WX_LACK_MEDIA_FILE_TYPE = 41005;
    public static final int WX_LACK_MEDIA_ID = 41006;
    public static final int WX_LACK_SUB_MENU_DATA = 41007;
    public static final int WX_ACCESS_TOKEN_EXPIRES = 42001;
    public static final int WX_NEED_GET_REQUEST = 43001;
    public static final int WX_NEED_POST_REQUEST = 43002;
    public static final int WX_NEED_HTTPS_REQUEST = 43003;
    public static final int WX_MEDIA_FILE_EMPTY = 44001;
    public static final int WX_POST_DATA_EMPTY = 44002;
    public static final int WX_NEWS_MESSAGE_CONTENT_EMPTY = 44003;
    public static final int WX_VIDEO_MESSAGE_SIZE_OVER_LIMIT = 45001;
    public static final int WX_MESSAGE_OVER_LIMIT = 45002;
    public static final int WX_TITLE_OVER_LIMIT = 45003;
    public static final int WX_DECRIPTION_OVER_LIMIT = 45004;
    public static final int WX_LINK_OVER_LIMIT = 45005;
    public static final int WX_IMAGE_LINK_OVER_LIMIT = 45006;
    public static final int WX_VOICE_PLAY_TIME_OVER_LIMIT = 45007;
    public static final int WX_NEWS_MESSAGE_OVER_LIMIT = 45008;
    public static final int WX_INTERFACE_CALL_OVER_LIMIT = 45009;
    public static final int WX_CREATE_MENU_SIZE_OVER_LIMIT = 45010;
    public static final int WX_NO_EXIST_MEDIA_DATA = 46001;
    public static final int WX_NO_EXIST_MENU_VERSION = 46002;
    public static final int WX_NO_EXIST_MENU_DATA = 46003;
    public static final int WX_NO_EXIST_USER = 46004;
    public static final int WX_PARSE_JSON_OR_XML_ERROR = 47001;
    public static final int WX_API_FUNCTION_NO_AUTH = 48001;
    public static final int WX_USER_NOT_AUTH_API = 50001;

    public static final String OAUTH_SCOPE_BASE = "snsapi_base";
    public static final String OAUTH_SCOPE_USERINFO = "snsapi_userinfo";

    public static final String BASE_AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?"
            + "appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=SMART#wechat_redirect";
    public static final String WX_MOCK_REDIRECT_URI = "{redirectUri}";

}