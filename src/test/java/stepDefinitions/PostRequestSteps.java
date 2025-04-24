package stepDefinitions;

import utils.ExcelReader;

import utils.ExcelWriter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONObject;

import context.TestContext;

public class PostRequestSteps {
    private static final Logger LOGGER = Logger.getLogger(PostRequestSteps.class.getName());
    private String requestBody;
    private final TestContext context;
    
    public PostRequestSteps(TestContext context) {
        this.context = context;
    }

    @Given("User loads the request body from Excel {string}, sheet {string}, row {int}")
    public void loadRequestBodyFromExcel(String filePath, String sheetName, int rowIndex) {
    	context.rowIndex = rowIndex;
        LOGGER.info("Loading request body from Excel: " + filePath);
        Map<String, String> data = ExcelReader.readData(filePath, sheetName, rowIndex);

        JSONObject location = new JSONObject();
        location.put("lat", Double.parseDouble(data.get("lat")));
        location.put("lng", Double.parseDouble(data.get("lng")));

        List<String> types = Arrays.stream(data.get("types").split(","))
                                   .map(String::trim)
                                   .filter(s -> !s.isEmpty())
                                   .toList();

        JSONObject request = new JSONObject();
        request.put("location", location);
        request.put("accuracy", Double.parseDouble(data.get("accuracy")));
        request.put("name", data.get("name"));
        request.put("phone_number", data.get("phone_number"));
        request.put("address", data.get("address"));
        request.put("types", types);
        request.put("website", data.get("website"));
        request.put("language", data.get("language"));

        requestBody = request.toString();
        LOGGER.info("Request body constructed from Excel: " + requestBody);
    }

    
    @When("User sends a POST request to {string}")
    public void sendPostRequest(String endpoint) {
        String fullUrl = RestAssured.baseURI + endpoint + "?key=" + context.queryParams.getString("key");
        LOGGER.info("Sending POST request to: " + fullUrl);

        context.response = context.request
            .queryParams(context.queryParams.toMap())
            .body(requestBody)
            .when()
            .post(fullUrl);

        LOGGER.info("Response body: " + context.response.getBody().asPrettyString());

        // Extract and save place_id
        String placeId = context.response.jsonPath().getString("place_id");
        LOGGER.info("Extracted place_id: " + placeId);

        try {
            // Save to Excel: use existing ExcelWriter
            String excelPath = "src/test/resources/data/PostData.xlsx"; // Adjust if needed
            String sheetName = "PostData";
            int rowIndex = context.rowIndex; // <-- make sure you're storing this from the feature step

            ExcelWriter.writeData(excelPath, sheetName, rowIndex, "place_id", placeId);
            LOGGER.info("place_id written to Excel successfully");

        } catch (IOException e) {
            LOGGER.severe("Error saving place_id: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
