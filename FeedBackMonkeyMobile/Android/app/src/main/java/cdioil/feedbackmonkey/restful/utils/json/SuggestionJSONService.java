package cdioil.feedbackmonkey.restful.utils.json;

import com.google.gson.annotations.SerializedName;

/**
 * JSONService that represents an Suggestion.
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
     * Image bytes.
     */
    @SerializedName(value = "imageBytes", alternate = {"IMAGEBYTES", "imagebytes"})
    private byte[] encodedImageBytes;
    /**
     * Builds a SuggestionJSONService object receiving the suggestion text and a String representing an images bytes.
     *
     * @param imageBytes bytes of an image
     */
    public SuggestionJSONService(String suggestionText, byte[] imageBytes) {
        this.suggestionText = suggestionText;
        this.encodedImageBytes = imageBytes;
    }
}
