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
		5. Create a review using the request URL http://localhost:8080/feedbackmonkeyapi/review/createReview/authenticationToken/surveyDatabaseID
		HTTP Method: POST
        authenticationToken - use the returned value in the login 
        surveyDatabaseID - use the ID of the survey in the bootstrap module
		6. Answer all the questions in the review using the request URl http://localhost:8080/feedbackmonkeyapi/review/answerQuestion/authenticationToken/false/binary/surveyID/reviewID
		HTTP Method: PUT
		surveyID - use the ID of the survey in the bootstrap module
		reviewID - use the ID of the review obtained in the previous request
		7. Submit a suggestion using the request URL http://localhost:8080/feedbackmonkeyapi/review/submitSuggestion/authenticationToken/surveyID/reviewID
		HTTP Method: PUT
		use the following body in JSON format:
            {
	            "suggestion":your_suggestion_here
            }
		
*/

/* if review id is not found in the database*/

pm.test("Status code is 404", function () {
    pm.response.to.have.status(404);
});

pm.test("Response should be an error", function(){
    pm.response.to.be.error;
    pm.response.to.have.jsonBody;
    pm.response.to.have.body("{\n\t\"reviewnotfound\":\"true\"\n}");
});

/* if review is not finished (one or more questions were not answered) or review already has a suggestion*/

pm.test("Status code is 412", function () {
    pm.response.to.have.status(412);
});

pm.test("Response should be an error", function(){
    pm.response.to.be.error;
    pm.response.to.have.jsonBody;
    pm.response.to.have.body("{\n\t\"reviewdoesnotmeetconditions\":\"true\"\n}");
});

/* if everything goes according to plan */

pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response should be okay to process", function(){
    pm.response.to.not.be.error;
});


