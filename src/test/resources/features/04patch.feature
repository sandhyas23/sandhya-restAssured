    @patch
Feature: PATCH User API
  
    Background: Admin login with Basic Auth
     Given Admin is authorized
   
 
 
 
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
    |noAuth|