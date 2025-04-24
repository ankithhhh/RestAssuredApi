package context;

import io.restassured.specification.RequestSpecification;

import io.restassured.response.Response;
import org.json.JSONObject;

public class TestContext {
    public RequestSpecification request;
    public Response response;
    public JSONObject queryParams;
    public JSONObject pathParams;
    public int rowIndex;
    
    private String placeId;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

}
