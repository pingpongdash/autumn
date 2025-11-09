package jp.kernelpanic.autumn.autumnapis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "auth")
@PropertySource(value = "classpath:user.yaml", factory = YamlPropertySourceFactory.class)
public class AuthProperties {

    private List<Map<String, String>> users;

    public List<Map<String, String>> getUsers() {
        return users;
    }

    public void setUsers(List<Map<String, String>> users) {
        this.users = users;
    }
}
