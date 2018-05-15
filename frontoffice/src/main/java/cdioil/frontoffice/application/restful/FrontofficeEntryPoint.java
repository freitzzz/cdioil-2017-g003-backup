package cdioil.frontoffice.application.restful;

import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Class that represents the frontoffice entry point
 * <br>Based on example given by Jersey
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 5.0 of FeedbackMonkey
 */
public class FrontofficeEntryPoint {
    /**
     * Constant that represents the package that holds all RESTful services
     */
    private static final String RESTFUL_SERVICES_PACKAGE="cdioil.frontoffice.application.restful";
    // Base URI the Grizzly HTTP server will listen on
    private static final String BASE_URI = "http://localhost:8080/frontoffice/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in codeine.jerseyworkshop package
        final ResourceConfig rc = new ResourceConfig().packages(RESTFUL_SERVICES_PACKAGE);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}
