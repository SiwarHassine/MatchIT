package com.c2s.dsif.filter;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.micrometer.common.util.StringUtils;


@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
   @Autowired
   private RouteValidator  validator;
   
   HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory ();
   //private RestTemplate template;
   @Autowired
   RestTemplate template = new RestTemplate(factory);
    public AuthenticationFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) ->{
            if(isSecured(exchange.getRequest())){
                String token = extractToken(exchange.getRequest());
                System.out.println("Token : "+ token);
                if (StringUtils.isEmpty(token)) {
                    throw new RuntimeException("Missing authorization header");
                }
                /*if (!validateToken(token)) {
                    throw new RuntimeException("Invalid or expired token");
                }*/
                if (!validateAuthority(token, exchange.getRequest().getPath().value())) {
                    throw new RuntimeException("Invalid or expired token or User does not have the required authority for this route");
                }
            }

            return chain.filter(exchange);

        } );
    }
    private boolean isSecured(ServerHttpRequest request) {
        return validator.isSecured.test(request);
    }

    private String extractToken(ServerHttpRequest request) {
        List<String> authorizationHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
            String authorizationHeader = authorizationHeaders.get(0);
            System.out.println("--------------"+authorizationHeader);
           if (authorizationHeader != null && authorizationHeader.trim().startsWith("Bearer")) {
            	
                return authorizationHeader.substring(7).trim();
            }
        }
        return null;
    }

    private boolean validateToken(String token) {
        String validationUrl = "http://localhost:8082/user/validate?token=" + token;
        try {
            ResponseEntity<Boolean> response = template.getForEntity(validationUrl, Boolean.class);
            return response.getBody() != null && response.getBody();
        } catch (Exception e) {
            System.out.println("Token validation failed: {}"+ e.getMessage());
            return false;
        }
    }

    private boolean validateAuthority(String token, String requestedRoute) {
        String authorityUrl = "http://localhost:8082/user/authority?token=" + token + "&route=" + requestedRoute;
        try {
            ResponseEntity<Boolean> response = template.getForEntity(authorityUrl, Boolean.class);
            return response.getBody() != null && response.getBody();
        } catch (Exception e) {
            System.out.println("Authority validation failed: {}"+ e.getMessage());
            return false;
        }
    }

    public static class Config{
    }
}
