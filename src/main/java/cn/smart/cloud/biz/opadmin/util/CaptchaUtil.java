package cn.smart.cloud.biz.opadmin.util;

import cn.smart.cloud.biz.opadmin.controller.login.security.WxSecurityAction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

/**
 * 验证码生成器类：可生成数字、字母及二者混合类型的验证码，配置文件的key为CONF_CAPTCHA。
 * <p>
 * 支持自定义验证码字符数量，支持自定义验证码图片的大小，支持自定义需排除的特殊字符，支持自定义干扰线的数量，支持自定义验证码图文颜色。
 *
 * @author junzai
 * @version 1.0 2010-01-19
 */
public class CaptchaUtil {

    /**
     * 验证码类型为数字（0-9）。
     */
    public static final int CODETYPE_NUM_ONLY = 0;

    /**
     * 验证码类型为字母（大小写字母混合）。
     */
    public static final int CODETYPE_LETTER_ONLY = 1;

    /**
     * 验证码类型为数字与字母混合。
     */
    public static final int CODETYPE_ALL_MIXED = 2;

    /**
     * 验证码类型为数字与大写字母混合。
     */
    public static final int CODETYPE_NUM_UPPER = 3;

    /**
     * 验证码类型为数字与小写字母混合。
     */
    public static final int CODETYPE_NUM_LOWER = 4;

    /**
     * 验证码类型为大写字母。
     */
    public static final int CODETYPE_UPPER_ONLY = 5;

    /**
     * 验证码类型为小写字母。
     */
    public static final int CODETYPE_LOWER_ONLY = 6;

    private CaptchaUtil() {

    }

    /**
     * 生成验证码字符串。
     *
     * @param codeType     验证码类型，参见本类的静态属性。
     * @param codeCount    验证码长度，大于0的整数。
     * @param excludeCodes 需排除的特殊字符（仅对数字和字母混合型验证码有效，无需排除则为null）。
     * @return 验证码字符串。
     */
    public static String generateCodeString(int codeType, int codeCount, String excludeCodes) {

        String configedCodeType = "";
        String configedCodeCount = "";
        String configedExcludeCodes = "";

        // 如果未设置codeType，则从配置文件中获取，否则以设置的值为准。
        if (!(codeType == CaptchaUtil.CODETYPE_ALL_MIXED || codeType == CaptchaUtil.CODETYPE_LETTER_ONLY
                || codeType == CaptchaUtil.CODETYPE_LOWER_ONLY || codeType == CaptchaUtil.CODETYPE_NUM_LOWER
                || codeType == CaptchaUtil.CODETYPE_NUM_ONLY || codeType == CaptchaUtil.CODETYPE_NUM_UPPER || codeType == CaptchaUtil.CODETYPE_UPPER_ONLY)) {
            if (StringUtils.isNotBlank(configedCodeType)) {
                try {
                    codeType = Integer.parseInt(configedCodeType);
                } catch (Exception e) {
                }
            }

            if (!(codeType == CaptchaUtil.CODETYPE_ALL_MIXED || codeType == CaptchaUtil.CODETYPE_LETTER_ONLY
                    || codeType == CaptchaUtil.CODETYPE_LOWER_ONLY || codeType == CaptchaUtil.CODETYPE_NUM_LOWER
                    || codeType == CaptchaUtil.CODETYPE_NUM_ONLY || codeType == CaptchaUtil.CODETYPE_NUM_UPPER || codeType == CaptchaUtil.CODETYPE_UPPER_ONLY)) {
                codeType = CaptchaUtil.CODETYPE_ALL_MIXED;
            }
        }

        // 如果未设置codeCount，则从配置文件中获取，否则以设置的值为准。
        if (codeCount <= 0) {
            if (StringUtils.isNotBlank(configedCodeCount)) {
                try {
                    codeCount = Integer.parseInt(configedCodeCount);
                } catch (Exception e) {
                }
            }

            if (codeCount <= 0) {
                return "";
            }
        }

        // 如果未设置excludeCodes，则从配置文件中获取，否则以设置的值为准。
        if (StringUtils.isBlank(excludeCodes)) {
            if (StringUtils.isNotBlank(configedExcludeCodes)) {
                excludeCodes = configedExcludeCodes;
            } else {
                excludeCodes = "";
            }
        }

        int i = 0;
        StringBuffer codeBuffer = new StringBuffer();
        Random r = new Random();

        switch (codeType) {
            // 仅数字。
            case CODETYPE_NUM_ONLY:
                while (i < codeCount) {
                    int t = r.nextInt(10);
                    if (excludeCodes == null || excludeCodes.indexOf(t + "") < 0) {
                        codeBuffer.append(t);
                        i++;
                    }
                }
                break;

            // 仅字母。
            case CODETYPE_LETTER_ONLY:
                while (i < codeCount) {
                    int t = r.nextInt(123);
                    if ((t >= 97 || t >= 65 && t <= 90) && (excludeCodes == null || excludeCodes.indexOf((char) t) < 0)) {
                        codeBuffer.append((char) t);
                        i++;
                    }
                }
                break;

            // 数字与字母的混合。
            case CODETYPE_ALL_MIXED:
                while (i < codeCount) {
                    int t = r.nextInt(123);
                    if ((t >= 97 || t >= 65 && t <= 90 || t >= 48 && t <= 57)
                            && (excludeCodes == null || excludeCodes.indexOf((char) t) < 0)) {
                        codeBuffer.append((char) t);
                        i++;
                    }
                }
                break;

            // 数字与大写字母的混合。
            case CODETYPE_NUM_UPPER:
                while (i < codeCount) {
                    int t = r.nextInt(91);
                    if ((t >= 65 || t >= 48 && t <= 57) && (excludeCodes == null || excludeCodes.indexOf((char) t) < 0)) {
                        codeBuffer.append((char) t);
                        i++;
                    }
                }
                break;

            // 数字与小写字母的混合。
            case CODETYPE_NUM_LOWER:
                while (i < codeCount) {
                    int t = r.nextInt(123);
                    if ((t >= 97 || t >= 48 && t <= 57) && (excludeCodes == null || excludeCodes.indexOf((char) t) < 0)) {
                        codeBuffer.append((char) t);
                        i++;
                    }
                }
                break;

            // 仅大写字母。
            case CODETYPE_UPPER_ONLY:
                while (i < codeCount) {
                    int t = r.nextInt(91);
                    if (t >= 65 && (excludeCodes == null || excludeCodes.indexOf((char) t) < 0)) {
                        codeBuffer.append((char) t);
                        i++;
                    }
                }
                break;

            // 仅小写字母。
            case CODETYPE_LOWER_ONLY:
                while (i < codeCount) {
                    int t = r.nextInt(123);
                    if (t >= 97 && (excludeCodes == null || excludeCodes.indexOf((char) t) < 0)) {
                        codeBuffer.append((char) t);
                        i++;
                    }
                }
                break;

        }

        return codeBuffer.toString();
    }

    /**
     * 已有验证码，生成验证码图片。
     *
     * @param codeString       验证码字符串。
     * @param width            图片宽度。
     * @param height           图片高度。
     * @param lineCount        图片中干扰线的条数。
     * @param isRandomLocation 每个字符的高低位置是否随机。
     * @param backgroundColor  图片背景颜色，若为null，则采用随机颜色。
     * @param fontColor        字体颜色，若为null，则采用随机颜色。
     * @param lineColor        干扰线颜色，若为null，则采用随机颜色。
     * @return 图片缓存对象。
     */
    public static BufferedImage generateCodeImage(String codeString, int width, int height, int lineCount,
                                                  boolean isRandomLocation, Color backgroundColor, Color fontColor, Color lineColor) {

        String configedWidth = "";
        String configedHeight = "";
        String configedLineCount = "";
        String configedIsRandomLocation = "";
        String configedBackgroundColorR = "";
        String configedBackgroundColorG = "";
        String configedBackgroundColorB = "";
        String configedFontColorR = "";
        String configedFontColorG = "";
        String configedFontColorB = "";
        String configedLineColorR = "";
        String configedLineColorG = "";
        String configedLineColorB = "";

        if (StringUtils.isNotBlank(configedWidth)) {
            try {
                width = Integer.parseInt(configedWidth);
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotBlank(configedHeight)) {
            try {
                height = Integer.parseInt(configedHeight);
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotBlank(configedLineCount)) {
            try {
                lineCount = Integer.parseInt(configedLineCount);
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotBlank(configedIsRandomLocation)) {
            try {
                isRandomLocation = Boolean.parseBoolean(configedIsRandomLocation);
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotBlank(configedBackgroundColorR) && StringUtils.isNotBlank(configedBackgroundColorG)
                && StringUtils.isNotBlank(configedBackgroundColorB)) {
            try {
                backgroundColor = new Color(Integer.parseInt(configedBackgroundColorR), Integer
                        .parseInt(configedBackgroundColorG), Integer.parseInt(configedBackgroundColorB));
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotBlank(configedFontColorR) && StringUtils.isNotBlank(configedFontColorG)
                && StringUtils.isNotBlank(configedFontColorB)) {
            try {
                fontColor = new Color(Integer.parseInt(configedFontColorR), Integer.parseInt(configedFontColorG),
                        Integer.parseInt(configedFontColorB));
            } catch (Exception e) {
            }
        }
        if (StringUtils.isNotBlank(configedLineColorR) && StringUtils.isNotBlank(configedLineColorG)
                && StringUtils.isNotBlank(configedLineColorB)) {
            try {
                lineColor = new Color(Integer.parseInt(configedLineColorR), Integer.parseInt(configedLineColorG),
                        Integer.parseInt(configedLineColorB));
            } catch (Exception e) {
            }
        }

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();

        // 画背景图。
        graphics.setColor(backgroundColor == null ? getRandomColor() : backgroundColor);
        graphics.fillRect(0, 0, width, height);

        // 画干扰线。
        Random lineRandom = new Random();
        if (lineCount > 0) {
            int x = 0, y = 0, x1 = width, y1 = 0;

            for (int i = 0; i < lineCount; i++) {
                graphics.setColor(lineColor == null ? getRandomColor() : lineColor);

                y = lineRandom.nextInt(height);
                y1 = lineRandom.nextInt(height);

                graphics.drawLine(x, y, x1, y1);
            }
        }

        // int fontSize = (int)(height * 0.75);
        // int fx = height - fontSize;
        // int fy = fontSize;

        // 写验证码字符串。
        int codeCount = codeString == null ? 0 : codeString.length();
        int fontSize = (int) (width / (0.8 * codeCount + 0.6));
        int fx = (int) (0.2 * fontSize);
        int fy = fontSize;

        graphics.setFont(new Font(null, Font.PLAIN, fontSize));

        for (int i = 0; i < codeCount; i++) {
            // fy = isRandomLocation ? (int)((Math.random() * 0.3 + 0.6) *
            // height) : fy;
            fy = isRandomLocation ? (int) ((Math.random() * 0.3 + 0.6) * height) : fy;

            graphics.setColor(fontColor == null ? getRandomColor() : fontColor);
            graphics.drawString(codeString.charAt(i) + "", fx, fy);

            // fx += fontSize * 0.6;
            fx += fontSize * 0.9;
        }
        graphics.dispose();

        return bufferedImage;
    }

    /**
     * 生成验证码图片。
     *
     * @param codeType         验证码类型，参见本类的静态属性。
     * @param codeCount        验证码长度，大于0的整数。
     * @param excludeCodes     需排除的特殊字符（仅对数字和字母混合型验证码有效，无需排除则为null）。
     * @param width            图片宽度。
     * @param height           图片高度。
     * @param lineCount        图片中干扰线的条数。
     * @param isRandomLocation 每个字符的高低位置是否随机。
     * @param backgroundColor  图片背景颜色，若为null，则采用随机颜色。
     * @param fontColor        字体颜色，若为null，则采用随机颜色。
     * @param lineColor        干扰线颜色，若为null，则采用随机颜色。
     * @return 图片缓存对象。
     */
    public static BufferedImage generateCodeImage(int codeType, int codeCount, String excludeCodes, int width,
                                                  int height, int lineCount, boolean isRandomLocation, Color backgroundColor, Color fontColor, Color lineColor) {

        String codeString = generateCodeString(codeType, codeCount, excludeCodes);

        BufferedImage bufferedImage = generateCodeImage(codeString, width, height, lineCount, isRandomLocation,
                backgroundColor, fontColor, lineColor);

        return bufferedImage;
    }

    /**
     * 产生随机颜色。
     *
     * @return 随机颜色。
     */
    private static Color getRandomColor() {

        Random r = new Random();

        Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));

        return c;
    }
}
