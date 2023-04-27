package org.gemeenteamsterdam.api.security.filters.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponseBuilder {
    private final HttpServletResponse response;
    private final Exception exception;

    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public ErrorResponseBuilder(final HttpServletResponse response, final Exception exception) {
        this.response = response;
        this.exception = exception;
    }

    public ErrorResponseBuilder withStatus(HttpStatus status) {
        this.httpStatus = status;
        return this;
    }

    public void send() throws IOException {
        response.setHeader("error", exception.getMessage());
        response.setStatus(httpStatus.value());
        final Map<String, String> error = new HashMap<>();
        error.put("errorMessage", exception.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
