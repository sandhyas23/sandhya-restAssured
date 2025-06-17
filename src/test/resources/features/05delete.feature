 @delete
Feature: DELETE User API
  
    Background: Admin login with Basic Auth
     Given Admin is authorized
    
  
  
  @deleteuser
  Scenario Outline: delete a user
    Given "Delete" request with BaseURL and valid EndPoint from "<scenario>"
    When Admin sends Delete request
    Then Admin receives status code and valid response
    Examples:
   |scenario|
   |deleteValidUserId|
   |deleteValidUserFirstname|
   |invalidUserId|
   |wrongEndpoint|
    |noAuth|