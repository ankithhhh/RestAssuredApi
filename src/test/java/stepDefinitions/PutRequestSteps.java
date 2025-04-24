package stepDefinitions;

import utils.ExcelReader;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONObject;

import context.TestContext;

public class PutRequestSteps {
    private static final Logger LOGGER = Logger.getLogger(PutRequestSteps.class.getName());
    private String putRequestBody;
    private final TestContext context;
    
    public PutRequestSteps(TestContext context) {
        this.context = context;
    }

    @Given("User loads the put request body from Excel {string}, sheet {string}, row {int}")
    public void loadPutRequestBodyFromExcel(String filePath, String sheetName, int rowIndex) {
        LOGGER.info("Loading PUT request body from Excel: " + filePath + " sheet: " + sheetName + " row: " + rowIndex);
        Map<String, String> putData = ExcelReader.readData(filePath, sheetName, rowIndex);

        JSONObject request = new JSONObject();
        request.put("place_id", context.getPlaceId()); // place_id from previous step
        request.put("address", putData.get("address"));
        request.put("key", context.queryParams.getString("key")); // key from query param

        putRequestBody = request.toString();
        LOGGER.info("PUT request body constructed: " + putRequestBody);
    }
    
    @When("User sends a PUT request to {string}")
    public void sendPutRequest(String endpoint) {
        String fullUrl = RestAssured.baseURI + endpoint;
        LOGGER.info("Sending PUT request to: " + fullUrl);

        context.response = context.request
            .queryParams(context.queryParams.toMap())
            .body(putRequestBody)
            .when()
            .put(fullUrl);

        LOGGER.info("Response body: " + context.response.getBody().asPrettyString());
    }
}
