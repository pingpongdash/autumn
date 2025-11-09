package jp.kernelpanic.auth.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jp.kernelpanic.auth.core.filter.JwtAuthenticationFilter;
import jp.kernelpanic.auth.core.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityProperties securityProperties;
    // private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, SecurityProperties securityProperties) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.securityProperties = securityProperties;
        System.out.println("🛡️ SecurityConfig initialized.");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var permitAllPaths = securityProperties.getPermitAllPaths();
        var authenticatedPaths = securityProperties.getAuthenticatedPaths();

        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                if (permitAllPaths != null && !permitAllPaths.isEmpty()) {
                    auth.requestMatchers(permitAllPaths.toArray(new String[0])).permitAll();
                }
                if (authenticatedPaths != null && !authenticatedPaths.isEmpty()) {
                    auth.requestMatchers(authenticatedPaths.toArray(new String[0])).authenticated();
                }
                auth.anyRequest().denyAll();
            })
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                             UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(e -> e.authenticationEntryPoint(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
            ));

        return http.build();
    }
}
