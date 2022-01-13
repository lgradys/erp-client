package warehouse.erpclient.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import warehouse.erpclient.dto.LoginCredentials;
import warehouse.erpclient.dto.UserDTO;
import warehouse.erpclient.handler.LoginHandler;

public class LoginClient {

    private final String LOGIN_URL = "http://localhost:8080/login";

    private final RestTemplate restTemplate;

    public LoginClient() {
        restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public void processAuthentication(LoginCredentials loginCredentials, LoginHandler loginHandler) {
        simulateDelay(1000);
        ResponseEntity<?> responseEntity;
        try{
            responseEntity = restTemplate.postForEntity(LOGIN_URL, loginCredentials, UserDTO.class);
        } catch (HttpStatusCodeException exception) {
            responseEntity = new ResponseEntity<>(exception.getResponseBodyAsString(), exception.getResponseHeaders(), exception.getStatusCode());
        } catch (ResourceAccessException exception) {
            responseEntity = new ResponseEntity<>("Connection refused. Try again later!", HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //todo check another possible exception - isolation of server exceptions and lost connection on client side
        loginHandler.handle(responseEntity);
    }

    private void simulateDelay(long milliseconds) {
        //todo to delete
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
