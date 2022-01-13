package warehouse.erpclient.handler;

import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface LoginHandler {

    void handle(ResponseEntity<?> responseEntity);

}
