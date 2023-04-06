package se.sbab.assignment.apiclient;

import java.io.IOException;
import java.net.URISyntaxException;


public interface ApiEndPoint {
    String getApiResponse() throws URISyntaxException;


    String getFalBackDataResponse() throws IOException;
}
