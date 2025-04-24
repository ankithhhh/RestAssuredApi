Feature: Maps API GET Request using Excel

  @excel
  Scenario Outline: Verify GET request using Excel data
    Given I load the base URL from config
    And I set up the Maps API request
    And I load the place_id from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    And I load the query parameters from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    When I send a GET request to "/get/json"
    Then the response status code should be <statusCode>
    And I log the response body

    Examples: 
      | excelPath                             | sheet    | rowIndex | statusCode |
      | src/test/resources/data/PostData.xlsx | PostData |        1 |        200 |
      | src/test/resources/data/PostData.xlsx | PostData |        2 |        200 |
