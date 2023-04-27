package org.gemeenteamsterdam.api.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO Use constant
        if (request.getServletPath().equals("/login") || request.getServletPath().equals("/token/refresh")) {

            System.out.println("HALLLLLLOOOOO LOGIN AND REFRESH");
            filterChain.doFilter(request, response);
        } else {

            System.out.println("HALLLLLLOOOOO");

            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            // TODO Use constant
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                // Handle authorization
                final String token = authorizationHeader.substring("Bearer ".length());
                // TODO DUPLICATE, USE CONSTANT / UTIL CLASS. ENSURE SAME SECRET
                final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                final JWTVerifier verifier = JWT.require(algorithm).build();
                final DecodedJWT decodedJWT = verifier.verify(token);
                final String email = decodedJWT.getSubject();
                // TODO Use constant
                final String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                // TODO Refactor conversion
                Arrays.stream(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });
                final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
