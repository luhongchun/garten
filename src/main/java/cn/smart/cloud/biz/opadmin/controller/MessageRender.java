package cn.smart.cloud.biz.opadmin.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessageRender {

    private static Logger logger = Logger.getLogger(MessageRender.class);
    private static Gson gson = new Gson();
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    private static void renderToView(HttpServletResponse response, String paramString1, String paramString2) {
        try {
            HttpServletResponse localHttpServletResponse = response;
            localHttpServletResponse.setContentType(paramString1 + ";charset=UTF-8");
            localHttpServletResponse.getWriter().write(paramString2);
            localHttpServletResponse.getWriter().flush();
            localHttpServletResponse.getWriter().close();
        } catch (IOException localIOException) {
            logger.error("向Response输出{}时出错" + paramString2);
        }
    }

    public static void renderJson(HttpServletResponse response, Object paramObject) {
        renderToView(response, "text/json", gson.toJson(paramObject));
    }

    public static void renderHtml(HttpServletResponse response, String paramString) {
        renderToView(response, "text/html", paramString);
    }

    public static void renderText(HttpServletResponse response, String paramString) {
        renderToView(response, "text/plain", paramString);
    }
}