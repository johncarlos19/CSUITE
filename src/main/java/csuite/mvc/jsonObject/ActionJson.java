package csuite.mvc.jsonObject;

public class ActionJson {
    private long id;
    private String typeClass;
    private String action;
    private String detail;
    private long anotherID;


    public ActionJson() {
    }

    public ActionJson(long id, String typeClass, String action, String detail, long anotherID) {
        this.id = id;
        this.typeClass = typeClass;
        this.action = action;
        this.detail = detail;
        this.anotherID = anotherID;
    }



    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public long getAnotherID() {
        return anotherID;
    }

    public void setAnotherID(long anotherID) {
        this.anotherID = anotherID;
    }
}