Feature: Maps API DELETE Request using Excel

  @excel
  Scenario Outline: Verify DELETE request using Excel data
    Given User loads the base URL from config
    And User sets up the Maps API request
    And User loads the place_id from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    And User loads the delete request body
    And User loads the query parameters from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    When User sends a DELETE request to "/delete/json"
    Then the response status code should be <ExpectedStatusCode>
    And the response should contain "status" with value "<ExpectedMessage>"
    And User logs the response body

    Examples: 
      | excelPath                             | sheet    | rowIndex | ExpectedStatusCode | ExpectedMessage |
      | src/test/resources/data/PostData.xlsx | PostData |        1 |                200 | OK              |
      | src/test/resources/data/PostData.xlsx | PostData |        2 |                200 | OK              |
