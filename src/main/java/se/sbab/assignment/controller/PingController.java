package se.sbab.assignment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * Health Check End points
 */
@RestController
public class PingController {

    @RequestMapping(method = {RequestMethod.GET}, value = {"/ping"}, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String ping() {
        return "ok";
    }


    @RequestMapping(method = {RequestMethod.GET}, value = {"/version"}, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getVersion() {
        return "1.0";
    }

}
