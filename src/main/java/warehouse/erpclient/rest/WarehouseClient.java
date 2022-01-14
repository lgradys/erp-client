package warehouse.erpclient.rest;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class WarehouseClient {

    private final String WAREHOUSE_URL = "http://localhost:8080/warehouses";

    private final RestTemplate restTemplate;

    public WarehouseClient() {
        restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }
}
