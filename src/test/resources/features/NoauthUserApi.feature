@noauthAPI
Feature: User API
  
    Background: Admin login with No Auth
     Given Admin is not authorized

@noauthcreate
  Scenario Outline: create a new user
  Given "Post" request with BaseURL and valid EndPoint from "<scenario>"
  When Admin sends Post request
  Then Admin receives status code and valid response
  Examples:
   |scenario|
   |noAuth|
 

  @noauthget
  Scenario Outline: get all users and by user id or firstname
    Given "Get" request with BaseURL and valid EndPoint from "<scenario>"
    When Admin sends Get request
    Then Admin receives status code and valid response
    Examples:
   |scenario|
   |noAuth|
    
    
  @noauthput
  Scenario Outline: update a user
    Given "Put" request with BaseURL and valid EndPoint from "<scenario>"
    When Admin sends Put request
    Then Adminreceives status code and valid response
        Examples:
   |scenario|
   |noAuth|
   
   
   @noauthpatch
  Scenario Outline: patch update a user
    Given "Patch" request with BaseURL and valid EndPoint from "<scenario>"
    When Admin sends Patch request
    Then Admin receives status code and valid response
    Examples:
   |scenario|
   |noAuth|



  @noauthcdelete
  Scenario Outline: delete a user
    Given "Delete" request with BaseURL and valid EndPoint from "<scenario>"
    When Admin sends Delete request
    Then Admin receives status code and valid response
    Examples:
   |scenario|
   |noAuth|

  
