package se.sbab.assignment.apiclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.sbab.assignment.db.model.Businformation;
import se.sbab.assignment.db.model.Stopinformation;
import se.sbab.assignment.dto.BusJourResponse;
import se.sbab.assignment.dto.StopResponse;
import se.sbab.assignment.exceptionhandler.exceptions.BadApiResponseException;
import se.sbab.assignment.service.BusService;
import se.sbab.assignment.service.StopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
@Slf4j
public class ApiClient {

    final BusService busServiceImpl;

    final ApiEndPoint slBusJourApiEndPointimpl;

    final SiteApiEndPoint slSiteApiEndPointimpl;

    final StopService stopService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${application.api.offlineMode:false}")
    boolean offlineMode;


    @PostConstruct
    @Scheduled(cron = "0 0 0 * * ?") // can be read from any configuration files
    public void fetchDataAndPopulateDatabase() throws IOException {
        String responseBody = getApiResponseData();
        List<BusJourResponse> busJourResponseList = getBusJourResponseList(responseBody);
        persistBusJourData(busJourResponseList);
        log.info("Now populating Stops Details");
        String siteResponseBody = getStopApiResponseData();
        List<StopResponse> stopResponseList = getStopSiteResposneList(siteResponseBody);
        presistStopData(stopResponseList);
        log.info("Application is ready now");
    }


    private String getApiResponseData() throws IOException {
        String responseBody;
        if (offlineMode) {
            responseBody = slBusJourApiEndPointimpl.getFalBackDataResponse();
        } else {
            try {
                // call Api and fetchData
                responseBody = slBusJourApiEndPointimpl.getApiResponse();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                final String statusCode = jsonNode.get("StatusCode").toPrettyString();
                if (!statusCode.equalsIgnoreCase("0")) {
                    String cause = "Api response StatusCode= %s, errorMessage= %s";
                    cause = String.format(cause, jsonNode.get("StatusCode").toPrettyString(), jsonNode.get("Message").toPrettyString());
                    throw new BadApiResponseException(cause);
                }
                log.info("Successfully called api");
            } catch (Exception e) {
                log.error("failed calling  api due to error", e);
                // n.w. read or connection time out has occurred read cached file
                log.info("reading data from Cached File");
                responseBody = slBusJourApiEndPointimpl.getFalBackDataResponse();
            }
        }
        return responseBody;
    }

    private String getStopApiResponseData() throws IOException{
        String responseBody;
        if(offlineMode) {
            responseBody = slSiteApiEndPointimpl.getFalBackDataResponse();
        }else {
            try {
                // call Api and fetchData
                responseBody = slSiteApiEndPointimpl.getApiResponse();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                final String stausCode = jsonNode.get("StatusCode").toPrettyString();
                if (!stausCode.equalsIgnoreCase("0")) {
                    String cause = "Api response StatusCode= %s, errorMessage= %s";
                    cause = String.format(cause, jsonNode.get("StatusCode").toPrettyString(), jsonNode.get("Message").toPrettyString());
                    throw new BadApiResponseException(cause);
                }
                log.info("Successfully called api");
            } catch (Exception e) {
                log.error("failed calling  api due to error", e);
                // n.w. read or connection time out has occurred read cached file
                log.info("reading data from Cached File");
                responseBody = slBusJourApiEndPointimpl.getFalBackDataResponse();
            }
        }
        return responseBody;
    }


    private void persistBusJourData(List<BusJourResponse> busJourResponseList) {
        final List<Businformation> businformationList = busJourResponseList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        busServiceImpl.refresh(businformationList);
        log.info("Saved all businfo  " + businformationList.size());
    }

    private void presistStopData(List<StopResponse> stopResponseList){
        final List<Stopinformation> stopInformaitonList = stopResponseList.stream()
                .map(this::convertStopDto)
                .collect(Collectors.toList());
        stopService.refresh(stopInformaitonList);
        log.info("Saved all stop info  " + stopInformaitonList.size());
    }


    private List<BusJourResponse> getBusJourResponseList(String responseBody) throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        final String statusCode = jsonNode.get("StatusCode").toPrettyString();
        if (statusCode.equalsIgnoreCase("0")) {
            return getBusJourResponsesData(objectMapper, jsonNode);
        }

        return List.of();
    }


    private List<StopResponse> getStopSiteResposneList(String responseBody) throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        final String statusCode = jsonNode.get("StatusCode").toPrettyString();
        if (statusCode.equalsIgnoreCase("0")) {
            return getStopJourResponseData(objectMapper, jsonNode);
        }
        return List.of();

    }

    private List<BusJourResponse> getBusJourResponsesData(ObjectMapper objectMapper, JsonNode jsonNode) throws JsonProcessingException {
        String result = jsonNode.get("ResponseData").get("Result").toPrettyString().toLowerCase();
        return objectMapper.readValue(result.toLowerCase(), new TypeReference<List<BusJourResponse>>() {
        });
    }

    private List<StopResponse> getStopJourResponseData(ObjectMapper objectMapper, JsonNode jsonNode) throws JsonProcessingException {
        String result = jsonNode.get("ResponseData").get("Result").toPrettyString().toLowerCase();

        return objectMapper.readValue(result.toLowerCase(), new TypeReference<List<StopResponse>>() {

        });
    }


    private Businformation convertToDto(BusJourResponse busJourResponse) {
        final Businformation businformation = new Businformation();
        businformation.setBusnumber(busJourResponse.getLinenumber());
        businformation.setBusstopnumber(busJourResponse.getJourneypatternpointnumber());
        businformation.setDirectioncode(busJourResponse.getDirectioncode());
        return businformation;
    }


    private Stopinformation convertStopDto(StopResponse stopResponse){
        final Stopinformation stopInformation = new Stopinformation();
        stopInformation.setStopid(stopResponse.getSiteid());
        stopInformation.setStopname(stopResponse.getSitename());
        stopInformation.setStopareanumber(stopResponse.getStopareanumber());
        return stopInformation;
    }
}
