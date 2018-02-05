package cn.smart.cloud.biz.opadmin.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AttendCardGartenUserEntity {

    private String id;
    private String cardId;
    private int state;
    private int roleType;
    private String gartenId;
    private String gartenUserId;
    private String gartenUserName;
    private String gartenClassId;
    private String gartenClassName;
    private String createDate;
    private Date date;
    private Date dateExit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public String getGartenId() {
        return gartenId;
    }

    public void setGartenId(String gartenId) {
        this.gartenId = gartenId;
    }

    public String getGartenUserId() {
        return gartenUserId;
    }

    public void setGartenUserId(String gartenUserId) {
        this.gartenUserId = gartenUserId;
    }

    public String getGartenUserName() {
        return gartenUserName;
    }

    public void setGartenUserName(String gartenUserName) {
        this.gartenUserName = gartenUserName;
    }

    public String getGartenClassId() {
        return gartenClassId;
    }

    public void setGartenClassId(String gartenClassId) {
        this.gartenClassId = gartenClassId;
    }

    public String getGartenClassName() {
        return gartenClassName;
    }

    public void setGartenClassName(String gartenClassName) {
        this.gartenClassName = gartenClassName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
        setDate(new Date(Long.parseLong(this.createDate)));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateExit() {
        return dateExit;
    }

    public void setDateExit(Date dateExit) {
        this.dateExit = dateExit;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
