package cn.smart.cloud.biz.opadmin.entity;

public class SfService extends BaseEntity {

    private String name;
    private String description;
    private SfServiceCategory category;
    private SfServiceLevel level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SfServiceCategory getCategory() {
        return category;
    }

    public void setCategory(SfServiceCategory category) {
        this.category = category;
    }

    public SfServiceLevel getLevel() {
        return level;
    }

    public void setLevel(SfServiceLevel level) {
        this.level = level;
    }

}