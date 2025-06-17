@post
Feature: POST User API
  
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
    |noAuth|