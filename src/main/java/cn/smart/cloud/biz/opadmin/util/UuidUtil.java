package cn.smart.cloud.biz.opadmin.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public final class UuidUtil {

    public static String randomWithoutHyphen() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String randomWithMd5Encrypted() {
        return DigestUtils.md5Hex(randomWithoutHyphen());
    }
}