Feature: Maps API PUT Request using Excel

  @excel
  Scenario Outline: Verify PUT request using Excel data
    Given User loads the base URL from config
    And User sets up the Maps API request
    And User loads the place_id from Excel "<excelPath>", sheet "<sheetForPost>", row <rowIndex>
    And User loads the query parameters from Excel "<excelPath>", sheet "<sheetForPost>", row <rowIndex>
    And User loads the put request body from Excel "<excelPath>", sheet "<sheetForPut>", row <rowIndex>
    When User sends a PUT request to "/update/json"
    Then the response status code should be <ExpectedStatusCode>
    And the response should contain "msg" with value "<ExpectedMessage>"
    And User logs the response body

    Examples: 
      | excelPath                             | sheetForPost | sheetForPut | rowIndex | ExpectedStatusCode | ExpectedMessage              |
      | src/test/resources/data/PostData.xlsx | PostData     | PutData     |        1 |                200 | Address successfully updated |
      | src/test/resources/data/PostData.xlsx | PostData     | PutData     |        2 |                400 | Address successfully updated |
