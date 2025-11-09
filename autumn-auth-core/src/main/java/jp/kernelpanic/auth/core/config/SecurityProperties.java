package jp.kernelpanic.auth.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private List<String> permitAllPaths;
    private List<String> authenticatedPaths;

    public List<String> getPermitAllPaths() {
        return permitAllPaths;
    }

    public void setPermitAllPaths(List<String> permitAllPaths) {
        this.permitAllPaths = permitAllPaths;
    }

    public List<String> getAuthenticatedPaths() {
        return authenticatedPaths;
    }

    public void setAuthenticatedPaths(List<String> authenticatedPaths) {
        this.authenticatedPaths = authenticatedPaths;
    }
}


// package jp.kernelpanic.auth.core.config;

// import java.util.List;
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// @ConfigurationProperties(prefix = "security")
// public class SecurityProperties {

//     private List<String> permitAllPaths;
//     private List<String> authenticatedPaths;

//     public List<String> getPermitAllPaths() {
//         return permitAllPaths;
//     }

//     public void setPermitAllPaths(List<String> permitAllPaths) {
//         this.permitAllPaths = permitAllPaths;
//     }

//     public List<String> getAuthenticatedPaths() {
//         return authenticatedPaths;
//     }

//     public void setAuthenticatedPaths(List<String> authenticatedPaths) {
//         this.authenticatedPaths = authenticatedPaths;
//     }
// }
