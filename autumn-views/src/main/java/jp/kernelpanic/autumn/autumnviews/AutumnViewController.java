package jp.kernelpanic.autumn.autumnviews;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;


// OpenAPI (Swagger / Springdoc)
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

// @RestController
@Controller
public class AutumnViewController {

    @GetMapping("/")
    public String showIndex(
        Model model,
        HttpServletRequest request,
        @RequestParam(name = "theme", defaultValue = "dark") String theme) {
        String clientIp = getClientIpAddress(request);
        model.addAttribute("currentTheme", theme);
        model.addAttribute("clientIp", clientIp);
        return "index";
    }

    @Operation(
        summary = "Access dashboard",
        security = { @SecurityRequirement(name = "bearerAuth") }
    )
    @GetMapping("/dashboard")
    public String showDashboard(
            Model model,
            HttpServletRequest request,
            @RequestParam(name = "theme", defaultValue = "dark") String theme,
            @AuthenticationPrincipal UserDetails user) {
        String currentPath = request.getRequestURI();
        String clientIp = getClientIpAddress(request);

        String username = (user != null) ? user.getUsername() : "anonymous";
        System.out.println("Accessed by: " + username);

        model.addAttribute("currentPath", currentPath);
        model.addAttribute("currentTheme", theme);
        model.addAttribute("clientIp", clientIp);
        model.addAttribute("username", username);
        return "dashboard";
    }


    private String getClientIpAddress(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || "unknown".equalsIgnoreCase(xfHeader)) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
