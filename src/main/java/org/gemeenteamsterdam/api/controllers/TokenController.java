package org.gemeenteamsterdam.api.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gemeenteamsterdam.api.entities.Role;
import org.gemeenteamsterdam.api.entities.User;
import org.gemeenteamsterdam.api.security.filters.response.ErrorResponseBuilder;
import org.gemeenteamsterdam.api.services.UserService;
import org.gemeenteamsterdam.api.services.UserServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
@Slf4j
public class TokenController {
    private final UserService userService;

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("Starting refresh token trip");
        // TODO Use constant
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // Handle authorization
                final String refreshToken = authorizationHeader.substring("Bearer ".length());
                // TODO DUPLICATE, USE CONSTANT / UTIL CLASS. ENSURE SAME SECRET
                final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                final JWTVerifier verifier = JWT.require(algorithm).build();
                final DecodedJWT decodedJWT = verifier.verify(refreshToken);
                final String email = decodedJWT.getSubject();
                final Optional<User> optionalUser = userService.findByEmail(email);

                if (optionalUser.isEmpty())
                    return;

                final User user = optionalUser.get();

                // TODO Refactor duplication
                final String accessToken = JWT.create()
                        // TODO Ensure if username is unique
                        .withSubject(user.getEmail())
                        // TODO Use constant
                        .withExpiresAt(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                        .withIssuer(request.getRequestURL().toString())
                        // TODO Use constant
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                // TODO Refactor duplication
                final Map<String, String> tokens = new HashMap<>();
                // TODO Use constants
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                // Return tokens in response body, in JSON format
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                new ErrorResponseBuilder(response, exception)
                        .withStatus(HttpStatus.FORBIDDEN)
                        .send();
            }
        } else {
            System.out.println("Invalid auth token.");
        }
    }
}
