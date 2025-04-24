Feature: Maps API POST Request using Excel

  @excel
  Scenario Outline: Verify POST request using Excel data
    Given User loads the base URL from config
    And User sets up the Maps API request
    And User loads the request body from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    And User loads the query parameters from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    When User sends a POST request to "/add/json"
    Then the response status code should be <ExpectedStatusCode>
    And the response should contain "status" with value "<ExpectedStatus>"
    And User logs the response body

    Examples: 
      | excelPath                             | sheet    | rowIndex | ExpectedStatusCode | ExpectedStatus |
      | src/test/resources/data/PostData.xlsx | PostData |        1 |                200 | OK             |
      | src/test/resources/data/PostData.xlsx | PostData |        2 |                200 | OK             |      