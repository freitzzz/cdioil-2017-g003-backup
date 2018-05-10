package cdioil.feedbackmonkey.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * RESTRequest class that performs a RESTful request on a certain URL
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 5.0 of FeedbackMonkey
 */

public final class RESTRequest {
    /**
     * Constant that represents the JSON media type (application/json)
     */
    private static final String JSON_MEDIA_TYPE="application/json";
    /**
     * RequestBuilder with the current request builder
     */
    private Request.Builder currentRequestBuilder;
    /**
     * RequestBody with the current request body
     */
    private RequestMediaType currentMediaType;
    /**
     * String with the current request body
     */
    private String currentRequestBody;

    /**
     * Creates a new RESTRequest with a certain URL which requests are going to be sent in
     * @param httpURL String with the request url
     * @return RESTRequest with the REST request that allows the user to define the variation of the REST request
     */
    public static RESTRequest create(String httpURL){return new RESTRequest().initializeRequest(httpURL);}

    /**
     * Performs a POST request on the requested URL
     * @return Response with the POST response or null if an error ocurred
     */
    public Response POST(){
        this.currentRequestBuilder.post(createRequestBody(getMediaType(currentMediaType),currentRequestBody));
        try{
            return sendRequest();
        }catch(IOException e){
            return null;
        }
    }
    /**
     * Performs a GET request on the requested URL
     * @return Response with the GET response or null if an error ocurred
     */
    public Response GET(){
        this.currentRequestBuilder.get();
        try{
            return sendRequest();
        }catch(IOException e){
            return null;
        }
    }
    /**
     * Method that sets the current request media type as a certain RequestMediaType
     * @param requestMediaType RequestMediaType enum with the media type of the request
     * @return RESTRequest with the new media type
     */
    public RESTRequest withMediaType(RequestMediaType requestMediaType){
        this.currentMediaType=requestMediaType;
        return this;
    }

    public RESTRequest withBody(String requestBody){
        this.currentRequestBody=requestBody;
        return this;
    }

    /**
     * Method that sends a certain request to the targeted URL according
     * to the request built
     * @return Response with the request response
     * @throws IOException IOException if an error occures while sending the request
     */
    private Response sendRequest() throws IOException {
        return new OkHttpClient().newCall(currentRequestBuilder.build()).execute();
    }
    /**
     * Method that initializes the REST request
     * @param url String with the request URL
     * @return RESTRequest with the initialized rest request
     */
    private RESTRequest initializeRequest(String url){
        currentRequestBuilder=new Request.Builder();
        currentRequestBuilder.url(url);
        return this;
    }
    /**
     * Method that creates a Request Body of a certain media type
     * @param mediaType MediaType with the request media type
     * @param body String with the request body
     * @return RequestBody with the newly created request body
     */
    private RequestBody createRequestBody(MediaType mediaType, String body){
        return RequestBody.create(mediaType, body);
    }

    /**
     * Method that returns the MediaType of a RequestMediaType
     * @param requestMediaType RequestMediaType enum with the enum of the media type
     * @return MediaType with the equivalent of a RequestMediaType
     */
    private MediaType getMediaType(RequestMediaType requestMediaType){
        switch(requestMediaType){
            case JSON:
                return MediaType.parse(JSON_MEDIA_TYPE);
            default:
                return null;
        }
    }

    /**
     * Enum class that represents the request media types available for REST requests
     */
    public enum RequestMediaType{
        JSON{@Override public String toString(){return "JSON";}}
    }

    /**
     * Hides default constructor
     */
    private RESTRequest(){}
}
