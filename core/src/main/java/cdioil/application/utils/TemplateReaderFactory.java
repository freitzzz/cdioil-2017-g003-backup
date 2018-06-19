package cdioil.application.utils;

import cdioil.files.CommonFileExtensions;
import java.io.IOException;

/**
 * Class responsible for building instances of TemplateReader.
 *
 * @author António Sousa [1161371]
 */
public final class TemplateReaderFactory {

    /**
     * Creates a new instance of TemplateReader.
     * @param filePath template file's absolute path
     * @return TemplateReader instance based on the file's extension
     * @throws java.io.IOException if an error occurred while reading the file
     */
    public static TemplateReader create(String filePath) throws IOException{
        if (filePath.endsWith(CommonFileExtensions.XML_EXTENSION)) {
            return new XMLTemplateReader(filePath);
        }
        throw new IOException("Unsupported file");
    }

}
