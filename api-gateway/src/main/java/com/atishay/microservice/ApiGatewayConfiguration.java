package com.atishay.microservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewaRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/get").filters(f -> f.addRequestHeader("myurl", "headerdata"))
						.uri("http://httpbin.org:80"))
				.route(r -> r.path("/currency-exchange/**").uri("lb://currency-exchange"))
				.route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-feign/**").uri("lb://currency-conversion")).route(
						p -> p.path("/currency-conversion-new/**")
								.filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)",
										"/currency-conversion-feign/${segment}"))
								.uri("lb://currency-conversion"))
				.build();
	}
}
