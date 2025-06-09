@API
Feature: User API
  
    Background: Admin login with Basic Auth
     Given The User is authorized

@postuser
  Scenario Outline: Add a new user
  Given the user has valid request header
  When the user send Post request with valid endpoint
  Then The user receives success status code 201 and valid response

  @getallusers 
  Scenario: Get all users
    Given the user has valid request header
    When user sends Get Request with valid endpoint
    Then The user receives success status code 200 and valid response

  @getuserbyid 
  Scenario: Get new user by user id
    Given the user has valid request header
    When user sends Get Request with valid endpoint for id
    Then The user receives success status code 200 and valid response

  @updateuser
  Scenario: edit a new user
    Given the user has valid request header
    When the user send Put request with valid endpoint
    Then The user receives success status code 200 and valid response

  @deleteuser
  Scenario: delete a new user
    Given the user has valid request header
    When the user send Delete request with valid endpoint
    Then The user receives success status code 200 and valid response

  
