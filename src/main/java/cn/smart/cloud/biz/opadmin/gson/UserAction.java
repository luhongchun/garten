package cn.smart.cloud.biz.opadmin.gson;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by jjj on 15-4-20.
 */
public class UserAction {

    private UserActionType acitonType;

    public UserActionType getAcitonType() {
        return acitonType;
    }

    public void setAcitonType(UserActionType acitonType) {
        this.acitonType = acitonType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}