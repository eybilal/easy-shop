package com.eybilal.apigateway.config;

// import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
// import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
// import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RoutingConfig {
    @Profile({"local-ide"})
    @Bean
    RouteLocator localIdeRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                                  .route(route -> route.path("/login")
                                      .uri("http://localhost:8081")
                                      .id("login")
                                  )
                                  .route(route -> route.path("/refresh-token")
                                      .uri("http://localhost:8081")
                                      .id("refresh-token")
                                  )
                                  .route(route -> route.path("/api/v1/customers/**")
                                      .uri("http://localhost:8085")
                                      .id("esm-customer-service")
                                  )
                                  .route(route -> route.path(
                                          "/api/v1/products/**",
                                          "/api/v1/categories/**"
                                      )
                                      .uri("http://localhost:8086")
                                      .id("esm-inventory-service")
                                  )
                                  .route(route -> route.path("/api/v1/orders/**")
                                      .uri("http://localhost:8087")
                                      .id("esm-order-service")
                                  )
                                  .route(route -> route.path(
                                        "/api/v2/products/**",
                                        "/api/v2/categories/**"
                                      )
                                      .uri("http://localhost:8091")
                                      .id("esedm-inventory-service")
                                  )
                                   // TODO circuit breaker for customers microservice
//                                    .route(route -> route.path("api/v1/customers")
//                                        .filters(filter ->
//                                            filter.circuitBreaker(c ->
//                                                c.setName("")
//                                                 .setFallbackUri("")
//                                            )
//                                        ).uri("").id("")
//                                    )
                                    .build();
    }

    @Profile({"local-docker"})
    @Bean
    RouteLocator localDockerRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                                  .route(route -> route.path("/login")
                                      .uri("lb://auth-server")
                                      .id("login")
                                  )
                                  .route(route -> route.path("/refresh-token")
                                      .uri("lb://auth-server")
                                      .id("refresh-token")
                                  )
                                  .route(route -> route.path("/api/v1/customers/**")
                                      .uri("lb://esm-customer-service")
                                      .id("esm-customer-service")
                                  )
                                  .route(route -> route.path(
                                        "/api/v1/products/**",
                                        "/api/v1/categories/**"
                                        )
                                      .uri("lb://esm-inventory-service")
                                      .id("esm-inventory-service")
                                  )
                                  .route(route -> route.path(
                                        "/api/v2/products/**",
                                                 "/api/v2/categories/**"
                                        )
                                        .uri("lb://esedm-inventory-service")
                                        .id("esedm-inventory-service")
                                  )
                                  .build();
    }

    @Bean
    public RouteLocator rapidApiRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                       // Rapid API
                       .route(route -> route.path("/countries/**")
                               .filters(filter ->
                                       filter.addRequestHeader(
                                               "x-rapidapi-key",
                                               ""			// TODO RAPID_API_KEY from .env file
                                       ).addRequestHeader(
                                               "x-rapidapi-host",
                                               "restcountries-v1.p.rapidapi.com"
                                       ).rewritePath(
                                               "/countries/(?<segment>.*)","/${segment}"
                                       ) // If the rest is not available or not reachable
                                               .circuitBreaker(c ->
                                                       c.setName("countries")
                                                               .setFallbackUri("forward:/countries/default")
                                               )
                               ).uri("https://restcountries-v1.p.rapidapi.com").id("countries")
                       )
               .build();
    }

//    @Bean
//    DiscoveryClientRouteDefinitionLocator dynamicRoutes(
//        ReactiveDiscoveryClient reactiveDiscoveryClient,
//        DiscoveryLocatorProperties discoveryLocatorProperties
//    ) {
//        return new DiscoveryClientRouteDefinitionLocator(
//            reactiveDiscoveryClient,
//            discoveryLocatorProperties
//        );
//    }
}
