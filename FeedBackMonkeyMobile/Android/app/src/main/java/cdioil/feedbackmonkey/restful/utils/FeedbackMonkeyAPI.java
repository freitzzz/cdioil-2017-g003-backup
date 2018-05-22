package cdioil.feedbackmonkey.restful.utils;

import com.fasterxml.jackson.xml.annotate.JacksonXmlProperty;
import com.fasterxml.jackson.xml.annotate.JacksonXmlRootElement;

import java.util.List;

/**
 * Class that holds the FeedbackMonkey API in a whole container
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public final class FeedbackMonkeyAPI {
    /**
     * Creates the FeedbackMonkey API
     */
    public static void create(){}
    /**
     * Hides private constructor
     */
    private FeedbackMonkeyAPI(){}
    @JacksonXmlRootElement(localName="feedbackmonkeyapi")
    private static class API{
        /**
         * String with the API entrypoint
         */
        @JacksonXmlProperty(localName="entrypoint",isAttribute = true)
        private String entryPoint;
        /**
         * List with all the API resources
         */
        @JacksonXmlProperty(localName = "resources",isAttribute = false)
        private List<Resource> resources;

        /**
         * Returns the API entrypoint
         * @return String with the API entrypoint
         */
        public String getEntryPoint(){return entryPoint;}

        /**
         * Returns the API resources
         * @return List with the API resources
         */
        public List<Resource> getResources(){return resources;}
    }
    /**
     * Class that defines a Resource XML element
     */
    private static class Resource{
        /**
         * String with the resource name
         */
        @JacksonXmlProperty(localName="name",isAttribute = true)
        private String name;
        /**
         * String with the resource path name
         */
        @JacksonXmlProperty(localName="path",isAttribute = true)
        private String path;
        /**
         * List with all the Resource sub resources
         */
        @JacksonXmlProperty(localName = "subresources")
        private List<Subresource> subresources;

        /**
         * Returns the Resource name
         * @return String with the resource name
         */
        public String getName(){return name;}
        /**
         * Returns the Resource path
         * @return String with the resource path
         */
        public String getPath(){return path;}

        /**
         * Returns the Resource sub resources
         * @return List with the resource sub resources
         */
        public List<Subresource> getSubResources(){return subresources;}
    }

    /**
     * Class that defines a Sub resource XML element
     */
    private static class Subresource{
        /**
         * String with the sub resource name
         */
        @JacksonXmlProperty(localName="name",isAttribute = true)
        private String name;
        /**
         * String with the sub resource path name
         */
        @JacksonXmlProperty(localName="path",isAttribute = true)
        private String path;
        /**
         * String with the sub resource method name
         */
        @JacksonXmlProperty(localName="method",isAttribute = false)
        private String method;
        /**
         * String with the sub resource data type name
         */
        @JacksonXmlProperty(localName="datatype",isAttribute = false)
        private String dataType;
        /**
         * Returns the Resource name
         * @return String with the resource name
         */
        public String getName(){return name;}
        /**
         * Returns the Resource path
         * @return String with the resource path
         */
        public String getPath(){return path;}
        /**
         * Returns the Resource method
         * @return String with the resource method
         */
        public String getMethod(){return method;}
        /**
         * Returns the Resource datatype
         * @return String with the resource datatype
         */
        public String getDataType(){return dataType;}
    }
}

