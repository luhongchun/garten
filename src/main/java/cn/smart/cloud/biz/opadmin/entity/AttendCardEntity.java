package cn.smart.cloud.biz.opadmin.entity;

public class AttendCardEntity {

    private String id;
    private String cardId;
    private int state;
    private String gartenUserId;
    private String createDate;

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

    public String getGartenUserId() {
        return gartenUserId;
    }

    public void setGartenUserId(String gartenUserId) {
        this.gartenUserId = gartenUserId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String dateStr) {
        this.createDate = dateStr;
    }

	public void setGartenClassId(String gartenClassId) {
		// TODO Auto-generated method stub
		
	}

	public void setGartenId(String gartenId) {
		// TODO Auto-generated method stub
		
	}
}
