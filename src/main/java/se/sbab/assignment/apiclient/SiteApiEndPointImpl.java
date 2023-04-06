package se.sbab.assignment.apiclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
@Slf4j
public class SiteApiEndPointImpl implements SiteApiEndPoint {
    final RestTemplate restTemplate;
    @Value("${application.sitcachedFilepath}")
    String cachedFilepath;
    @Value("${application.sitapiUrl}")
    String apiUrl;

    @Override
    public String getApiResponse() throws URISyntaxException {
        log.info("apiUrl =  " + apiUrl);
        URI uri = new URI(apiUrl);
        return restTemplate.getForEntity(uri, String.class).getBody();
    }

    @Override
    public String getFalBackDataResponse() throws IOException {
        return Files.readString(Paths.get(cachedFilepath));
    }


}
