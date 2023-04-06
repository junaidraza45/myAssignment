package se.sbab.assignment.apiclient;

import java.io.IOException;
import java.net.URISyntaxException;

public interface SiteApiEndPoint {
    String getApiResponse() throws URISyntaxException;

    String getFalBackDataResponse() throws IOException;
}
