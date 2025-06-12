@noauthAPI
Feature: User API
  
    Background: Admin login with No Auth
     Given the User is not authorized

@noauthcreate
  Scenario Outline: create a new user
  Given "Post" request with BaseURL and valid EndPoint from "<scenario>"
  When the user sends Post request
  Then The user receives status code and valid response
  Examples:
   |scenario|
   |noAuth|
 

  @noauthget
  Scenario Outline: get all users and by user id or firstname
    Given "Get" request with BaseURL and valid EndPoint from "<scenario>"
    When the user sends Get request
    Then The user receives status code and valid response
    Examples:
   |scenario|
   |noAuth|
    
    
  @noauthput
  Scenario Outline: update a user
    Given "Put" request with BaseURL and valid EndPoint from "<scenario>"
    When the user sends Put request
    Then The user receives status code and valid response
        Examples:
   |scenario|
   |noAuth|
   
   
   @noauthpatch
  Scenario Outline: patch update a user
    Given "Patch" request with BaseURL and valid EndPoint from "<scenario>"
    When the user sends Patch request
    Then The user receives status code and valid response
    Examples:
   |scenario|
   |noAuth|



  @noauthcdelete
  Scenario Outline: delete a user
    Given "Delete" request with BaseURL and valid EndPoint from "<scenario>"
    When the user sends Delete request
    Then The user receives status code and valid response
    Examples:
   |scenario|
   |noAuth|

  
