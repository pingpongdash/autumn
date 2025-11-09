package jp.kernelpanic.autumn.autumnapis.auth.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;


import jp.kernelpanic.autumn.autumnapis.auth.dto.LoginRequest;
// import jp.kernelpanic.autumn.autumnapis.auth.core.jwt.JwtTokenProvider;
import jp.kernelpanic.auth.core.jwt.JwtTokenProvider;
import jp.kernelpanic.autumn.autumnapis.config.AuthProperties;


@Service
public class AuthorizationService {

    private final JwtTokenProvider tokenProvider;

    // TODO use database
    private final AuthProperties authProperties;

    // @Value("${auth.users}")
    // private List<Map<String, String>> userList;

    private final Map<String, String> credentials = new HashMap<>();

    public AuthorizationService(JwtTokenProvider tokenProvider, AuthProperties authProperties) {
        this.tokenProvider = tokenProvider;
        // TODO use database
        this.authProperties = authProperties;
    }

    @PostConstruct
    public void init() {
        List<Map<String, String>> userList = authProperties.getUsers();
        System.out.println(userList);
        if (userList != null) {
            for (Map<String, String> user : userList) {
                credentials.put(user.get("username"), user.get("password"));
            }
            System.out.println("🔐 Loaded users from YAML: " + credentials.keySet());
        } else {
            System.err.println("⚠️ No users found in YAML configuration!");
        }
    }


    /**
     * Authroize
     * @param request login request
     * @return Authorize result
     */
    public String authorize(LoginRequest request) {
        String expectedPassword = credentials.get(request.getLoginId());
        boolean success = expectedPassword != null && expectedPassword.equals(request.getPassword());

        if (success) {
            System.out.println("✅ Access allow; ID=" + request.getLoginId());
            return tokenProvider.generateToken(request.getLoginId());
        } else {
            System.err.println("❌ Access deny: ID=" + request.getLoginId());
            return null;
        }

    }
}
