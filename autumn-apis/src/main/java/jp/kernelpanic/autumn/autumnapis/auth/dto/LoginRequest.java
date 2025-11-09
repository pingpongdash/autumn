package jp.kernelpanic.autumn.autumnapis.auth.dto;

public class LoginRequest {
    private String loginId;
    private String password;

    public LoginRequest() {}

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{loginId='" + loginId + "'}";
    }
}
