@API
Feature: User API
  
    Background: Admin login with Basic Auth
     Given The User is authorized

@postuser
  Scenario Outline: Add a new user
  Given the user has valid request header
  When the user send Post request from "<scenario>"
  Then The user receives status code and valid response
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
   

  #@getallusers 
  #Scenario: Get all users
    #Given the user has valid request header
    #When user sends Get Request with valid endpoint
    #Then The user receives success status code 200 and valid response

  @getuserbyid 
  Scenario Outline: Get new user by user id
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
   |invalidEndpoint|

  #@updateuser
  #Scenario: edit a new user
    #Given the user has valid request header
    #When the user send Put request with valid endpoint
    #Then The user receives success status code 200 and valid response

  #@deleteuser
  #Scenario Outline: delete a new user
    #Given the user has valid request header
    #When the user send Get request from "<scenario>"
    #Then The user receives status code and valid response
        #Examples:
   #|scenario|
   #|validUserId|
   #|invalidUserId|
   #|invalidEndpoint|

  
