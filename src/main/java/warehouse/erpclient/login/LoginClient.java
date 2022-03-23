package warehouse.erpclient.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import warehouse.erpclient.login.dto.LoginCredentials;
import warehouse.erpclient.login.dto.UserDTO;
import warehouse.erpclient.utils.dto.Error;
import warehouse.erpclient.utils.dto.RequestResult;

import java.util.List;

import static warehouse.erpclient.utils.PropertiesUtils.createUrl;

public class LoginClient {

    private final String LOGIN_PATH = "/login";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public LoginClient() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public void processAuthentication(LoginCredentials loginCredentials, LoginHandler loginHandler) {
        simulateDelay(1000);
        ResponseEntity<RequestResult<UserDTO>> responseEntity;
        try {
            responseEntity = sendRequest(loginCredentials);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            RequestResult<UserDTO> requestResult = new RequestResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(new Error(exception.getMessage())), List.of());
            responseEntity = new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
        }
        //todo check another possible exception - isolation of server exceptions and lost connection on client side
        loginHandler.handle(responseEntity);
    }

    private ResponseEntity<RequestResult<UserDTO>> sendRequest(LoginCredentials loginCredentials) throws JsonProcessingException {
        ResponseEntity<RequestResult<UserDTO>> responseEntity;
        try {
            ParameterizedTypeReference<RequestResult<UserDTO>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
            };
            responseEntity = restTemplate.exchange(createUrl(LOGIN_PATH), HttpMethod.POST, new HttpEntity<>(loginCredentials), parameterizedTypeReference);
        } catch (HttpStatusCodeException exception) {
            RequestResult<UserDTO> requestResult = objectMapper.readValue(exception.getResponseBodyAsString(), new TypeReference<>() {
            });
            responseEntity = new ResponseEntity<>(requestResult, HttpHeaders.EMPTY, HttpStatus.valueOf(requestResult.getStatus()));
        }
        return responseEntity;
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
