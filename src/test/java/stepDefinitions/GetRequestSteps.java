package stepDefinitions;

import io.cucumber.java.en.When;

import static io.restassured.RestAssured.*;

import java.util.logging.Logger;

import context.TestContext;

public class GetRequestSteps {

    private static final Logger LOGGER = Logger.getLogger(GetRequestSteps.class.getName());
    
    private TestContext context;

    public GetRequestSteps(TestContext context) {
        this.context = context;
    }

    @When("User sends a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        context.response = given()
        		.queryParams(context.queryParams.toMap())
        		.queryParam("place_id", context.getPlaceId())
        		.when()
        		.get(endpoint);
        LOGGER.info("Response body: " + context.response.asPrettyString());
    }
}
