package cn.smart.cloud.biz.opadmin.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UriUtil {

    private static final String DEFAULT_ENC = "utf8";

    private UriUtil() {
    }

    public static String appendParamsToCompleteUri(String uri, NameValuePair... param) {
        try {
            return new URIBuilder(uri).setParameters(param).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(String value) {
        return encode(value, DEFAULT_ENC);
    }

    public static String encode(String value, String enc) {
        try {
            return URLEncoder.encode(value, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String decode(String value) {
        return decode(value, DEFAULT_ENC);
    }

    public static String decode(String value, String enc) {
        try {
            return URLDecoder.decode(value, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }
}

		
