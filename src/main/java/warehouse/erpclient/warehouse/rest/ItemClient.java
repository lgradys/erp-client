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
import warehouse.erpclient.warehouse.dto.ItemDTO;

import java.util.List;
import java.util.Map;

import static warehouse.erpclient.utils.PropertiesUtils.createUrl;

public class ItemClient {

    private final String ITEMS_PATH = "/warehouses/{warehouseId}/items";
    private final String ITEM_PATH = "/warehouses/{warehouseId}/items/{itemId}";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ItemClient() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public ResponseEntity<RequestResult<ItemDTO>> getItems(String authorizationToken, long warehouseId) {
        Map<String, Long> uriVariables = createUriVariables(warehouseId);
        HttpEntity<ItemDTO> requestEntity = createRequestEntity(null, authorizationToken);
        return sendRequest(createUrl(ITEMS_PATH), HttpMethod.GET, requestEntity, uriVariables);
    }

    public ResponseEntity<RequestResult<ItemDTO>> deleteItem(String authenticationToken, long warehouseId, long itemId) {
        Map<String, Long> uriVariables = createUriVariables(warehouseId, itemId);
        HttpEntity<ItemDTO> requestEntity = createRequestEntity(null, authenticationToken);
        return sendRequest(createUrl(ITEM_PATH), HttpMethod.DELETE, requestEntity, uriVariables);
    }

    public ResponseEntity<RequestResult<ItemDTO>> editItem(String authenticationToken, ItemDTO itemDTO) {
        Map<String, Long> uriVariables = createUriVariables(itemDTO.getWarehouseId(), itemDTO.getId());
        HttpEntity<ItemDTO> requestEntity = createRequestEntity(itemDTO, authenticationToken);
        return sendRequest(createUrl(ITEM_PATH), HttpMethod.PUT, requestEntity, uriVariables);
    }

    public ResponseEntity<RequestResult<ItemDTO>> addItem(String authenticationToken, ItemDTO itemDTO) {
        Map<String, Long> uriVariables = createUriVariables(itemDTO.getWarehouseId());
        HttpEntity<ItemDTO> requestEntity = createRequestEntity(itemDTO, authenticationToken);
        return sendRequest(createUrl(ITEMS_PATH), HttpMethod.POST, requestEntity, uriVariables);
    }

    private ResponseEntity<RequestResult<ItemDTO>> sendRequest(String url, HttpMethod httpMethod, HttpEntity<ItemDTO> requestEntity, Map<String, Long> uriVariables) {
        ResponseEntity<RequestResult<ItemDTO>> responseEntity;
        try {
            responseEntity = createRequest(url, httpMethod, requestEntity, uriVariables);
        } catch (JsonProcessingException exception) {
            RequestResult<ItemDTO> requestResult = new RequestResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(new Error("Connection refused!")), List.of());
            responseEntity = new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
        }
        return responseEntity;
    }

    private HttpEntity<ItemDTO> createRequestEntity(ItemDTO itemDTO, String authorizationToken) {
        if (itemDTO != null) {
            return new HttpEntity<>(itemDTO, createHeaders(authorizationToken));
        } else {
            return new HttpEntity<>(createHeaders(authorizationToken));
        }
    }

    private Map<String, Long> createUriVariables(long warehouseId) {
        return Map.of("warehouseId", warehouseId);
    }

    private Map<String, Long> createUriVariables(long warehouseId, long itemId) {
        return Map.of("warehouseId", warehouseId, "itemId", itemId);
    }

    private HttpHeaders createHeaders(String authorizationToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", authorizationToken);
        return httpHeaders;
    }

    private ResponseEntity<RequestResult<ItemDTO>> createRequest(String url, HttpMethod httpMethod, HttpEntity<ItemDTO> httpEntity, Map<String, Long> uriVariables) throws JsonProcessingException {
        ResponseEntity<RequestResult<ItemDTO>> responseEntity;
        try {
            ParameterizedTypeReference<RequestResult<ItemDTO>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
            };
            responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, parameterizedTypeReference, uriVariables);
        } catch (HttpStatusCodeException exception) {
            RequestResult<ItemDTO> requestResult = objectMapper.readValue(exception.getResponseBodyAsString(), new TypeReference<>() {
            });
            responseEntity = new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
        }
        return responseEntity;
    }

}

