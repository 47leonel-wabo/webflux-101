package com.wbt.webflux101.functional.config;

import com.wbt.webflux101.functional.RequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouteConfig {

    private final RequestHandler requestHandler;

    public RouteConfig(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponse() {
        return RouterFunctions.route()
                .GET("/functional/math/square/{value}", this.requestHandler::squareRootHandler)
                .GET("/functional/math/table/{value}", this.requestHandler::tableHandler) // streaming by default
                .build();
    }

}
