package com.security;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Optional;

@Component
@Provider
public class SecurityFilter {

    private static final String AUTHORIZATION_PROPERTY = "API_KEY";

    public boolean check(HttpHeaders headers, String role) {
        Optional<String> apiKeyHeader = headers.getRequestHeader(AUTHORIZATION_PROPERTY).stream().findFirst();
        if (apiKeyHeader.isEmpty()) {
            return false;
        }
        List<APIKey> listApiKey = APIKey.find("apiKey", apiKeyHeader.get()).list();
        Optional<APIKey> first = listApiKey.stream().findFirst();
        return first.isPresent() && role.equals(first.get().role);
    }

}
