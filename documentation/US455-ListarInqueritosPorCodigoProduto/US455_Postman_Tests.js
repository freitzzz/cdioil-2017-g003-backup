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
*/

/* --------------------------------------    Test listSurveys    -------------------------------------- */

/*
Request URL to use for these tests: http://localhost8080/feedbackmonkeyapi/authentication/surveys/getSurveysByProductCode/{authenticationToken}/{code}
HTTP Method for all tests: GET
*/


// If there are surveys about the product which code was scanned
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response should be okay to process", function(){
    pm.response.to.not.be.error;
    pm.response.to.have.jsonBody;
})

// If no surveys were found
pm.test("Status code is 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Response should be okay to process", function(){
    pm.response.to.be.error;
    pm.response.to.have.jsonBody;
    pm.response.to.have.body("{\n\t\"noavailablesurveys\":\"true\"\n}");
})

// If no products were found
pm.test("Status code is 404", function () {
    pm.response.to.have.status(404);
});

pm.test("Response should be an error", function(){
    pm.response.to.be.error;
    pm.response.to.have.jsonBody;
    pm.response.to.have.body("{\n\t\"productnotfound\":\"true\"\n}");
});
