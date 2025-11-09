package jp.kernelpanic.autumn.autumnapis;


import java.util.Map;
import jakarta.servlet.http.Cookie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

// OpenAPI (Swagger / Springdoc)
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;



import jp.kernelpanic.autumn.autumnapis.auth.service.AuthorizationService;
import jp.kernelpanic.autumn.autumnapis.auth.dto.LoginRequest;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "HAL Core API", description = "api core")
public class AutumnApiController {

    private final AuthorizationService authorizationService;

    public AutumnApiController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Operation(
        summary = "User login with ID and password",
        description = "Authenticates user and returns a JWT token if credentials are valid."
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@ModelAttribute LoginRequest request) {
        System.out.println("Try login: ID=" + request.getLoginId());

        String jwtToken = authorizationService.authorize(request);
        if (jwtToken != null) {
            return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "token", jwtToken
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of(
                "status", "FAILURE",
                "message", "Access denied."
            ));
        }
    }
}

