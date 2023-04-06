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
public class ApiEndPointImpl implements ApiEndPoint {

    final RestTemplate restTemplate;
    @Value("${application.cachedFilepath}")
    String cachedFilepath;
    @Value("${application.apiUrl}")
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
