/*
	Run bootstrap module before running Postman tests
	Run FrontofficeEntryPoint main class from frontoffice module after running bootstrap
	Postman tests to assure the REST API is working properly
	Request URL to use for these tests: http://localhost8080/feedbackmonkeyapi/authentication/login
	HTTP Method for all tests: GET
*/


//Use the following body in JSON format for these tests
/*
{
	"email": "sonae.lover@sonae.pt",
	"password": "Password123"
}
*/

pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response should be okay to process", function(){
    pm.response.to.not.be.error;
    pm.response.to.have.jsonBody;
})


//Use the following body in JSON format for these tests
/*
{
	"email": "sonae.lover@sonae.pt",
	"password": "Password1234"
}
*/
pm.test("Status code is 401", function () {
    pm.response.to.have.status(401);
});

pm.test("Response should be an error", function(){
    pm.response.to.be.error;
    pm.response.to.have.jsonBody;
    pm.response.to.have.body("{\n\t\"invalidcredentials\":\"true\"\n}");
})


//Use the following body in JSON format for these tests
/*
{
	"email": "naoativado@email.com",
	"password": "Password123"
}
*/

pm.test("Status code is 400", function () {
    pm.response.to.have.status(400);
});

pm.test("Response should be an error", function(){
    pm.response.to.be.error;
    pm.response.to.have.jsonBody;
    pm.response.to.have.body("{\n\t\"activationcode\":\"required\"\n}");
})