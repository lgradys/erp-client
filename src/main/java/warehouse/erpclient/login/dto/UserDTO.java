package warehouse.erpclient.login.dto;

public class UserDTO {

    private String username;
    private String password;
    private String roleName;
    private boolean enabled;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String roleName, boolean enabled) {
        this.username = username;
        this.password = password;
        this.roleName = roleName;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
