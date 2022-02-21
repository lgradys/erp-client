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
import warehouse.erpclient.warehouse.dto.WarehouseDTO;

import java.util.List;

public class WarehouseClient {

    private final String WAREHOUSE_URL = "http://localhost:8080/warehouses";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WarehouseClient() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public ResponseEntity<RequestResult<WarehouseDTO>> getWarehousesList(String authorizationToken) {
        ResponseEntity<RequestResult<WarehouseDTO>> responseEntity;
        try {
            responseEntity = sendRequest(authorizationToken);
        } catch (JsonProcessingException exception) {
            RequestResult<WarehouseDTO> requestResult = new RequestResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(new Error("Connection refused!")), List.of());
            responseEntity = new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
        }
        return responseEntity;
    }

    private ResponseEntity<RequestResult<WarehouseDTO>> sendRequest(String authorizationToken) throws JsonProcessingException {
        ResponseEntity<RequestResult<WarehouseDTO>> responseEntity;
        try {
            ParameterizedTypeReference<RequestResult<WarehouseDTO>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
            };
            responseEntity = restTemplate.exchange(WAREHOUSE_URL, HttpMethod.GET, new HttpEntity<>(createHeaders(authorizationToken)), parameterizedTypeReference);
        } catch (HttpStatusCodeException exception) {
            RequestResult<WarehouseDTO> requestResult = objectMapper.readValue(exception.getResponseBodyAsString(), new TypeReference<>() {
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
