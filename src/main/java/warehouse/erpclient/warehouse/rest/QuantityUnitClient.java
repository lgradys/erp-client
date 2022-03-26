package warehouse.erpclient.warehouse.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import warehouse.erpclient.utils.dto.Error;
import warehouse.erpclient.utils.dto.RequestResult;

import java.util.List;

import static warehouse.erpclient.utils.PropertiesUtils.createUrl;

public class QuantityUnitClient {

    private final String QUANTITY_UNIT_PATH = "/quantityUnits";

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public QuantityUnitClient() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public ResponseEntity<RequestResult<String>> getQuantityUnitList(String authorizationToken) {
        ResponseEntity<RequestResult<String>> responseEntity;
        try {
            responseEntity = sendRequest(authorizationToken);
        } catch (JsonProcessingException exception) {
            RequestResult<String> requestResult = new RequestResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(new Error("Connection refused!")), List.of());
            responseEntity = new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
        }
        return responseEntity;
    }

    private ResponseEntity<RequestResult<String>> sendRequest(String authorizationToken) throws JsonProcessingException {
        ResponseEntity<RequestResult<String>> responseEntity;
        try {
            ParameterizedTypeReference<RequestResult<String>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
            };
            responseEntity = restTemplate.exchange(createUrl(QUANTITY_UNIT_PATH), HttpMethod.GET, new HttpEntity<>(createHeaders(authorizationToken)), parameterizedTypeReference);
        } catch (HttpStatusCodeException exception) {
            RequestResult<String> requestResult = objectMapper.readValue(exception.getResponseBodyAsString(), new TypeReference<>() {
            });
            responseEntity = new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
        }
        return responseEntity;
    }

    private HttpHeaders createHeaders(String authorizationToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", authorizationToken);
        return httpHeaders;
    }

}
