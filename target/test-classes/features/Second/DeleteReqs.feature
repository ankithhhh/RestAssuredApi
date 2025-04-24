Feature: Maps API DELETE Request using Excel

  @excel
  Scenario Outline: Verify DELETE request using Excel data
    Given I load the base URL from config
    And I set up the Maps API request
    And I load the place_id from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    And I load the delete request body
    And I load the query parameters from Excel "<excelPath>", sheet "<sheet>", row <rowIndex>
    When I send a DELETE request to "/delete/json"
    Then the response status code should be <statusCode>
    And the response should contain "status" with value "<message>"
    And I log the response body

    Examples: 
      | excelPath                             | sheet    | rowIndex | statusCode | message |
      | src/test/resources/data/PostData.xlsx | PostData |        1 |        200 | OK      |
      | src/test/resources/data/PostData.xlsx | PostData |        2 |        200 | OK      |
