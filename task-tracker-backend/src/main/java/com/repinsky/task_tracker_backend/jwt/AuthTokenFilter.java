package com.repinsky.task_tracker_backend.jwt;

import com.repinsky.task_tracker_backend.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.repinsky.task_tracker_backend.constants.Constant.BEARER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JWTTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromJwt(request);
            if (token != null) {
                log.debug("Token found: " + token);
                String validationError = jwtTokenUtil.validateJwtToken(token);
                if (validationError.equals("")) {
                    String userData = jwtTokenUtil.getUserDataFromToken(token);
                    log.debug("Token is valid for user: " + userData);

                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userData);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("Authentication set for user: " + userData);
                } else {
                    log.debug("Token validation failed: " + validationError);
                }
            }
        } catch (Exception e) {
            log.error("Could not set user authentication in security context", e);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER.getValue())) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
