package jp.kernelpanic.auth.core.filter;

import java.io.IOException;
import java.util.Collections;

// Servlet API
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Spring Security
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

// Spring Web and Filter
import org.springframework.web.filter.OncePerRequestFilter;

// import jp.kernelpanic.autumn.autumnapis.auth.core.jwt.JwtTokenProvider;
import jp.kernelpanic.auth.core.jwt.JwtTokenProvider;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
            this.jwtTokenProvider = jwtTokenProvider;
        }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        // When Cookie has a JWT
        // if (request.getCookies() != null) {
        //     for (Cookie cookie : request.getCookies()) {
        //         if ("AUTH_TOKEN".equals(cookie.getName())) {
        //             return cookie.getValue();
        //         }
        //     }
        // }

        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
            String userId = jwtTokenProvider.getUserIdFromToken(jwt);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
