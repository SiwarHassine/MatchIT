package com.c2s.dsif.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndPoint=Arrays.asList(
            "/user/authenticate",
            "/user/validate",
            "/user/registrate",
            "/eureka"
    );
    public Predicate<ServerHttpRequest> isSecured=
            request ->openApiEndPoint.stream().noneMatch(uri->request.getURI().getPath().contains(uri));

}
