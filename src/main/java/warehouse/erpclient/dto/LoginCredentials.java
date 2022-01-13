package warehouse.erpclient.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginCredentials {

    private String username;
    private String password;

}
