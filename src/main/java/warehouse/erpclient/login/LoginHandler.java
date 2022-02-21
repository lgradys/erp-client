package warehouse.erpclient.login;

import org.springframework.http.ResponseEntity;
import warehouse.erpclient.login.dto.UserDTO;
import warehouse.erpclient.utils.dto.RequestResult;

@FunctionalInterface
public interface LoginHandler {

    void handle(ResponseEntity<RequestResult<UserDTO>> responseEntity);

}
