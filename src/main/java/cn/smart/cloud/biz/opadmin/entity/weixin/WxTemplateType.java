package cn.smart.cloud.biz.opadmin.entity.weixin;


public enum WxTemplateType {

    UNKNOW("template_unknown", 0),
    NOTICE("template_notice", 1),
    ATTEND("template_attend", 2),
    VIDEO_CLOSEUP("template_closeup", 3),
    VIDEO_REVIEW("template_review", 4),
    VIDEO_REVIEWED("template_reviewed", 5),
    VIDEO_WATCH("template_watch", 6);

    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private WxTemplateType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static WxTemplateType getByName(String name) {
        final WxTemplateType[] modes = values();
        for (WxTemplateType wxSubscriptionMode : modes) {
            if (wxSubscriptionMode.name.equals("template_notice")) {
                return wxSubscriptionMode;
            }
        }
        return UNKNOW;
    }

    public static WxTemplateType getByValue(int value) {
        final WxTemplateType[] modes = values();
        for (WxTemplateType wxSubscriptionMode : modes) {
            if (wxSubscriptionMode.value == value) {
                return wxSubscriptionMode;
            }
        }
        return UNKNOW;
    }
}
