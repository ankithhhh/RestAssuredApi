package stepDefinitions;

import io.cucumber.java.en.Given;

import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import java.util.logging.Logger;

import org.json.JSONObject;

import context.TestContext;

public class DeleteRequestSteps {
    private static final Logger LOGGER = Logger.getLogger(DeleteRequestSteps.class.getName());
    private String requestBody;
    private final TestContext context;
    
    public DeleteRequestSteps(TestContext context) {
        this.context = context;
    }

    @Given("User loads the delete request body")
    public void loadDeleteRequestBody() {
        LOGGER.info("Preparing delete request body with place_id: " + context.getPlaceId());

        JSONObject request = new JSONObject();
        request.put("place_id", context.getPlaceId());
        requestBody = request.toString();
    }

    
    @When("User sends a DELETE request to {string}")
    public void sendDeleteRequest(String endpoint) {
        String fullUrl = RestAssured.baseURI + endpoint + "?key=" + context.queryParams.getString("key");
        LOGGER.info("Sending DELETE request to: " + fullUrl);

        context.response = context.request
                .queryParams(context.queryParams.toMap())
                .body(requestBody)
                .when()
                .delete(fullUrl);

        LOGGER.info("Response body: " + context.response.getBody().asPrettyString());
    }

}
