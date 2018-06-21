package cdioil.frontoffice.application.restful.json;

import com.google.gson.annotations.SerializedName;

/**
 * JSONService that represents a Suggestion.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public final class SuggestionJSONService {
    /**
     * String that holds the suggestion text a user gave.
     */
    @SerializedName(value = "suggestionText", alternate = {"SUGGESTIONTEXT", "suggestiontext"})
    private String suggestionText;
    /**
     * Image bytes that were encoded using Base64.
     */
    @SerializedName(value = "imageBytes", alternate = {"IMAGEBYTES", "imagebytes"})
    private String encodedImageBytes;
    /**
     * Builds a SuggestionJSONService object receiving the suggestion text and a String representing an images bytes.
     *
     * @param encodedImageBytes String that holds the Base64 encoded bytes of an image
     */
    public SuggestionJSONService(String suggestionText, String encodedImageBytes) {
        this.suggestionText = suggestionText;
        this.encodedImageBytes = encodedImageBytes;
    }
    /**
     * Builds a SuggestionJSONService object receiving only the suggestion text.
     *
     * @param suggestionText
     */
    public SuggestionJSONService(String suggestionText) {
        this.suggestionText = suggestionText;
    }
    /**
     * Returns the suggestion text.
     * 
     * @return String holding the suggestion text.
     */
    public String getSuggestionText(){
        return suggestionText;
    }
    /**
     * Returns the encoded image bytes String.
     * 
     * @return String holding the encoded image bytes.
     */
    public String getEncodedImageBytes(){
        return encodedImageBytes;
    }
}
