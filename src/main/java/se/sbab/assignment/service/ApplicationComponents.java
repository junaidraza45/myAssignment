package se.sbab.assignment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ApplicationComponents {

    @Value("${application.api.readTimeOut}")
    int readTimeOut;
    @Value("${application.api.connectionTimeOut}")
    int connectionTimeOut;


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate(getClientHttpRequestFactory());
    }


    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }


    private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory
                = new SimpleClientHttpRequestFactory();
        //setting Connect timeout
        clientHttpRequestFactory.setConnectTimeout(readTimeOut);

        //setting Read timeout
        clientHttpRequestFactory.setReadTimeout(connectionTimeOut);
        return clientHttpRequestFactory;
    }
}
