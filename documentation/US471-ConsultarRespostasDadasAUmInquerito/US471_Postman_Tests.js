/*
    ------------------------------------------------------------
 	   Postman tests to assure the REST API is working properly
    ------------------------------------------------------------ */

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
		5. To get the Success Response (200) you need to answer a survey completely beforehand. You also need to know the ID of the survey you answered.
		6. To get the other Responses you don't need to perform step 5.
*/

/* --------------------------------------    Test check User Review Answers -------------------------------------- */

/*
Request URL to use for these tests: http://localhost8080/feedbackmonkeyapi/reviews/userreviewmap/{authenticationToken}/{surveyID}
HTTP Method for all tests: GET
*/

// If the user has answered atleast one survey completely
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response should be okay to process", function(){
    pm.response.to.not.be.error;
    pm.response.to.have.jsonBody;
});

// If the user doesn't have any reviews for a specific survey
pm.test("Status code is 404", function () {
    pm.response.to.have.status(404);
});

pm.test("Response should be okay to process", function(){
    pm.response.to.be.error;
    pm.response.to.have.jsonBody;
    pm.response.to.have.body("{\n\t\"noreviewsfound\":\"true\"\n}");
})

// If the survey ID is invalid
pm.test("Status code is 404", function () {
    pm.response.to.have.status(404);
});

pm.test("Response should be okay to process", function(){
    pm.response.to.be.error;
    pm.response.to.have.jsonBody;
    pm.response.to.have.body("{\n\t\"surveynotfound\":\"true\"\n}");
})
