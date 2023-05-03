package org.gemeenteamsterdam.api.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // TODO Use constant

        final String username = request.getParameter("username");
        // TODO Use constant
        final String password = request.getParameter("password");
        log.info("Username: {} | Password: {}", username, password);
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        final User user = (User) authentication.getPrincipal();


        // TODO Save encrypted version of secret somewhere secure, load and decrypt (encrypted) secret, use it here
        // TODO DUPLICATE, USE CONSTANT / UTIL CLASS. ENSURE SAME SECRET
        final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        // TODO Refactor duplication
        final String accessToken = JWT.create()
                // TODO Ensure if username is unique
                .withSubject(user.getUsername())
                // TODO Use constant
                .withExpiresAt(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                .withIssuer(request.getRequestURL().toString())
                // TODO Use constant
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        final String refreshToken = JWT.create()
                // TODO Ensure if username is unique
                .withSubject(user.getUsername())
                // TODO Use constant
                .withExpiresAt(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        // TODO Refactor duplication
        final Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        // Return tokens in response body, in JSON format
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        // TODO Handle AuthenticationException with RestExceptionHandler. To let user know Authentication wasn't successful.
        throw failed;
    }
}
