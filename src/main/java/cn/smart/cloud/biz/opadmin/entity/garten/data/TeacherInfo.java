package cn.smart.cloud.biz.opadmin.entity.garten.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;

public class TeacherInfo extends BaseEntity{

    private String name;
    private String sex;
    private String className;
    private String phoneNum;
    private String gartenId;
    private String classId;
    private String status;
    

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	public String getGartenId() {
		return gartenId;
	}

	public void setGartenId(String gartenId) {
		this.gartenId = gartenId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
}