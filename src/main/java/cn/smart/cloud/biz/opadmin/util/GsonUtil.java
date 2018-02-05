package cn.smart.cloud.biz.opadmin.util;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

public final class GsonUtil {

    private static Logger logger = Logger.getLogger(GsonUtil.class);

    private static Gson cachedGson;

    private GsonUtil() {
    }

    private static Gson gson() {
        if (cachedGson == null) {
            cachedGson = GsonFactory.createGenericGson();
        }
        return cachedGson;
    }

    public static String toJson(Object src) {
        try {
            return gson().toJson(src);
        } catch (Exception e) {
            logger.error("toJson " + e);
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return gson().fromJson(json, classOfT);
        } catch (Exception e) {
            logger.error("fromJson " + e);
        }
        return null;
    }

    public static <T> T fromJsonForWechat(String json, Class<T> classOfT) {
        try {
            json = new String(json.getBytes("iso8859-1"), "UTF-8");
            return gson().fromJson(json, classOfT);
        } catch (Exception e) {
            logger.error("fromJson " + e);
        }
        return null;
    }

}