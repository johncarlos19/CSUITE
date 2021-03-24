package csuite.mvc.jsonObject;

public class GraphJson {
    private String key;
    private float value;

    public GraphJson(String key, float value) {
        this.key = key;
        this.value = value;
    }

    public GraphJson() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
