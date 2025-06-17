 @put
Feature: PUT User API
  
    Background: Admin login with Basic Auth
     Given Admin is authorized
     
     
  @updateuser
  Scenario Outline: update a user
    Given "Put" request with BaseURL and valid EndPoint from "<scenario>"
    When Admin sends Put request
    Then Admin receives status code and valid response
    Examples:
   |scenario|
   |validDataAll|
   |existingContactNo|
   |existingEmailId|
   |invalidContactNo|
   |invalidEmailId|
   |invalidUserId|
   |wrongEndpoint|
    |noAuth|
   