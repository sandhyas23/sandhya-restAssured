@get
Feature: GET User API
  
    Background: Admin login with Basic Auth
     Given Admin is authorized
     
        
     
 @getuser
  Scenario Outline: get all users and by user id or firstname
    Given "Get" request with BaseURL and valid EndPoint from "<scenario>"
    When Admin sends Get request
    Then Admin receives status code and valid response
    Examples:
   |scenario|
   |allUsers|
   |validUserId|
   |validUserFirstname|
   |invalidUserId|
   |invalidUserFirstname|
   |wrongEndpoint|
    |noAuth|