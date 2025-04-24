Feature: Maps API POST Request using Excel

  @excel
  Scenario Outline: Verify POST request using Excel data
    Given I load the base URL from config
    And I set up the Maps API request
    And I load the request body from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    And I load the query parameters from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    When I send a POST request to "/add/json"
    Then the response status code should be <statusCode>
    And the response should contain "status" with value "<status>"
    And I log the response body

    Examples: 
      | excelPath                             | sheet    | rowIndex | statusCode | status |
      | src/test/resources/data/PostData.xlsx | PostData |        1 |        200 | OK     |
      | src/test/resources/data/PostData.xlsx | PostData |        2 |        200 | OK     |