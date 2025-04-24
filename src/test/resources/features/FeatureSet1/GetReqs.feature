Feature: Maps API GET Request using Excel

  @excel
  Scenario Outline: Verify GET request using Excel data
    Given User loads the base URL from config
    And User sets up the Maps API request
    And User loads the place_id from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    And User loads the query parameters from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    When User sends a GET request to "/get/json"
    Then the response status code should be <ExpectedStatusCode>
    And User logs the response body

    Examples: 
      | excelPath                             | sheet    | rowIndex | ExpectedStatusCode |
      | src/test/resources/data/PostData.xlsx | PostData |        1 |                200 |
      | src/test/resources/data/PostData.xlsx | PostData |        2 |                200 |
