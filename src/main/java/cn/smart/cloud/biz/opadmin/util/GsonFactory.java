package cn.smart.cloud.biz.opadmin.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

final class GsonFactory {
    private GsonFactory() {
    }

    private static GsonBuilder newGsonBuilder() {
        return new GsonBuilder();
    }

    public static Gson createGenericGson() {
        return newGsonBuilder().disableHtmlEscaping().create();
    }
}