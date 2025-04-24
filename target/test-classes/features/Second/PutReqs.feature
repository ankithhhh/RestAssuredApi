Feature: Maps API PUT Request using Excel

  @excel
  Scenario Outline: Verify PUT request using Excel data
    Given I load the base URL from config
    And I set up the Maps API request
    And I load the place_id from Excel "<excelPath>", sheet "<sheetForPost>", row <rowIndex>
        And I load the query parameters from Excel "<excelPath>", sheet "<sheetForPost>", row <rowIndex>
    And I load the put request body from Excel "<excelPath>", sheet "<sheetForPut>", row <rowIndex>
    When I send a PUT request to "/update/json"
    Then the response status code should be <statusCode>
    And the response should contain "msg" with value "<message>"
    And I log the response body

    Examples: 
      | excelPath                             | sheetForPost | sheetForPut | rowIndex | statusCode | message                      |
      | src/test/resources/data/PostData.xlsx | PostData     | PutData     |        1 |        200 | Address successfully updated |
      | src/test/resources/data/PostData.xlsx | PostData     | PutData     |        2 |        200 | Address successfully updated |
