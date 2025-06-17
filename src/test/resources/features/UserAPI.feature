@API
Feature: User API
  
    Background: Admin login with Basic Auth
     Given Admin is authorized

@createuser
  Scenario Outline: create a new user
  Given "Post" request with BaseURL and valid EndPoint from "<scenario>"
  When Admin sends Post request
  Then Admin receives status code and valid response
  Examples:
   |scenario|
   |validDataAll|
   |existingContactNo|
   |existingEmailId|
   |validMandatory|
   |invalidFirstname|
   |invalidLastname|
   |invalidContactNo|
   |invalidEmailId|
   |invalidPlotNumber|
   
   

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
   
   
   @patchuser
  Scenario Outline: patch update a user
    Given "Patch" request with BaseURL and valid EndPoint from "<scenario>"
    When Admin sends Patch request
    Then Admin receives status code and valid response
    Examples:
   |scenario|
   |validFirstNamePatch|
	 |validEmailIdPatch|
   |validPlotPatch|
   |existingContactNo|
   |existingEmailId|
   |invalidContactNo|
   |invalidEmailId|
   |wrongEndpoint|
   |invalidUserId|
   |invalidPlotNumber|


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

  
