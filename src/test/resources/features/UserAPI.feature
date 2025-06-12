@API
Feature: User API
  
    Background: Admin login with Basic Auth
     Given The User is authorized

@postuser
  Scenario Outline: add a new user
  Given the user has valid request header
  When the user send Post request from "<scenario>"
  Then The user receives status code and valid response
  Examples:
   |scenario|
   |validDataAll|
   |existingContactNo|
   |existingEmailId|
   #|validMandatory|
   |invalidFirstname|
   |invalidLastname|
   |invalidContactNo|
   |invalidEmailId|
   

  @getuserbyid 
  Scenario Outline: get new user by user id
    Given the user has valid request header
    When the user send Get request from "<scenario>"
    Then The user receives status code and valid response
    Examples:
   |scenario|
   |allUsers|
   |validUserId|
   |validUserFirstname|
   |invalidUserId|
   |invalidUserFirstname|
   |wrongEndpoint|

    
    
  @updateuser
  Scenario Outline: update a new user
    Given the user has valid request header
    When the user send Put request from "<scenario>"
    Then The user receives status code and valid response
        Examples:
   |scenario|
   |validDataAll|
   |existingContactNo|
   |existingEmailId|
   |invalidContactNo|
   |invalidEmailId|
   |invalidUserId|
   |wrongEndpoint|


  @deleteuser
  Scenario Outline: delete a new user
    Given the user has valid request header
    When the user send Delete request from "<scenario>"
    Then The user receives status code and valid response
        Examples:
   |scenario|
   |validUserId|
   |invalidUserId|
   |wrongEndpoint|

  
