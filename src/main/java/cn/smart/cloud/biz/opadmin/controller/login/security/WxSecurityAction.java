package cn.smart.cloud.biz.opadmin.controller.login.security;

import cn.smart.cloud.biz.opadmin.controller.MessageRender;
import cn.smart.cloud.biz.opadmin.util.CaptchaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/security")
public class WxSecurityAction {

    private Logger logger = LoggerFactory.getLogger(WxSecurityAction.class);

    private static final Color defaultBackgroundColor = Color.RED;
    private static final Color defaultFontColor = Color.WHITE;

    private String mVerifyCode = "";

    @RequestMapping("captcha")
    public String captcha(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        // 设置浏览器不缓存本页。
        response.setHeader("Cache-Control", "no-cache");

        // 生成验证码，写入用户session。
        mVerifyCode = CaptchaUtil.generateCodeString(CaptchaUtil.CODETYPE_NUM_UPPER, 4, "0oOilJI1");

        // 获取session。
        HttpSession session = request.getSession();
        if (session != null) {
            session.setAttribute("captchaVerifyCode", mVerifyCode);
        }

        // 输出验证码给客户端。
        response.setContentType("image/jpeg");
        logger.info("Generate verify code: " + mVerifyCode);
        BufferedImage bim = CaptchaUtil.generateCodeImage(mVerifyCode, 90, 30, 0, false, defaultBackgroundColor,
                defaultFontColor, null);
        logger.info("Generate verify code image: " + bim);
        ImageIO.write(bim, "JPEG", response.getOutputStream());
        return "NONE";
    }

    @RequestMapping("auth/{verifyCode}")
    public void auth(@PathVariable String verifyCode,
                     HttpServletRequest request,
                     HttpServletResponse response) throws IOException {
        logger.info("auth, verifyCode:" + verifyCode + ", mVerifyCode:" + mVerifyCode);
        if (verifyCode.equalsIgnoreCase(mVerifyCode)) {
            MessageRender.renderHtml(response, "true");
        } else {
            MessageRender.renderHtml(response, "false");
        }
    }

}