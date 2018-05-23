/*
    --------------------------------------------------------------------------------
 	            Postman tests to assure the REST API is working properly
    -------------------------------------------------------------------------------- */

/*
    Before running Postman Tests...
	    1. Run bootstrap module
	    2. Run FrontofficeEntryPoint main class from frontoffice module 
        3. Login with the request URL http://localhost8080/feedbackmonkeyapi/authentication/login and
        use the following body in JSON format:
            {
	            "email": "joao@email.com",
	            "password": "Password123"
            }
        4. Use the generated code by the login request to authenticate yourself in the following steps
*/

/* ------------------------------------------------    Test createReview    ------------------------------------------------ */
    
/*
        Creates a new review about a survey. Returns the ID of the created Review.

        Request URL: http://localhost:8080/feedbackmonkeyapi/review/createReview/authenticationToken/surveyDatabaseID
        HTTP Method: POST

        authenticationToken - use the returned value in the login 
        surveyDatabaseID - use the ID of the survey in the bootstrap module

        Expected body in JSON format:
            {
              "reviewID": generated database ID of the review
            }
*/

pm.test("Status code is 201", function () {
    pm.response.to.have.status(201);
});

pm.test("Response should be okay to process", function(){
    pm.response.to.not.be.error;
    pm.response.to.have.jsonBody;
});

/* If surveyDatabaseID is invalid (non existent survey in the database), the tests to run are the following: */

pm.test("Status code is 404", function () {
    pm.response.to.have.status(404);
});

pm.test("Response should be an error", function(){
    pm.response.to.be.error;
    pm.response.to.have.jsonBody;
    pm.response.to.have.body("{\n\t\"invalidsurvey\":\"true\"\n}");
});


/* ------------------------------------------------    Test showQuestion    ------------------------------------------------ */

/*
        Returns the current question of the survey.

        Request URL: http://localhost8080/feedbackmonkeyapi/review/showQuestion/authenticationToken/surveyDatabaseID/reviewID
        HTTP Method: GET
          
            authenticationToken - use the returned value in the login
            surveyDatabaseID - use the ID of the survey in the bootstrap module
            reviewID - use the returned value in createReview
*/

pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response should be okay to process", function(){
    pm.response.to.not.be.error;
    pm.response.to.have.jsonBody;
});


/* ------------------------------------------------    Test answerQuestion    ------------------------------------------------ */ 
    
/*
        Allows the user to answer a question.
       
        Request URL: http://localhost:8080/feedbackmonkeyapi/review/answerQuestion/authenticationToken/false/binary/surveyDatabaseID/reviewID
        HTTP Method: PUT

            authenticationToken - use the returned value in the login 
            surveyDatabaseID - use the ID of the survey in the bootstrap module
            reviewID - use the returned value in createReview
*/

pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response should be okay to process", function(){
    pm.response.to.not.be.error;
    pm.response.to.have.jsonBody;
});

/* If the option is invalid, for example, with the following request URL:

    http://localhost:8080/feedbackmonkeyapi/review/answerQuestion/authenticationToken/invalid/binary/surveyDatabaseID/reviewID

    since "invalid" isn't a valid option for a binary question (only "true" ou "false" are acceptable options).
 */

pm.test("Status code is 401", function () {
    pm.response.to.have.status(401);
});

pm.test("Response should be an error", function(){
    pm.response.to.be.error;
    pm.response.to.have.jsonBody;
    pm.response.to.have.body("{\n\t\"invalidoption\":\"true\"\n}");
});

/* If the review is null, it will be impossible to save to the database. In that case, error 400 is returned: */

pm.test("Status code is 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Response should be an error", function(){
    pm.response.to.be.error;
    pm.response.to.have.jsonBody;
    pm.response.to.have.body("{\n\t\"invalidreview\":\"true\"\n}");
});

