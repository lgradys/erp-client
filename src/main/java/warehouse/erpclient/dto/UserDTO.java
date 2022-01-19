package warehouse.erpclient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDTO {

    private String username;
    private String password;
    private String roleName;
    private boolean authenticated;

}
