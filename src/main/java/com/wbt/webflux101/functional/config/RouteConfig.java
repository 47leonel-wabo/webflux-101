package com.wbt.webflux101.functional.config;

import com.wbt.webflux101.exception.InvalidInputException;
import com.wbt.webflux101.functional.RequestHandler;
import com.wbt.webflux101.functional.error.ExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
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
    public RouterFunction<ServerResponse> highLevelRoute() {
        return RouterFunctions.route()
                .path("/functional/math", this::serverResponse)
                .build();
    }

    private RouterFunction<ServerResponse> serverResponse() {
        return RouterFunctions.route()
                .GET("/square/{value}", this.requestHandler::squareRootHandler)
                .GET("/square/{value}/valid", this.requestHandler::squareRootWithValidation2Handler)
                .GET("/square/{value}/validation", RequestPredicates.path("*/1?"), this.requestHandler::squareRootWithValidation2Handler)
                .GET("/square/{value}/validation", req -> ServerResponse.badRequest().bodyValue("Only values from 10 to 19 allowed!"))
                .GET("/table/{value}", this.requestHandler::tableHandler) // streaming by default
                .POST("/multiply", this.requestHandler::multiplicationHandler)
                .onError(InvalidInputException.class, ExceptionHandler.handle())
                .build();
    }

}
