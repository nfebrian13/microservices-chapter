package com.kelaskoding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.kelaskoding.webclient.CustomerClient;
import com.kelaskoding.webclient.ProductClient;

import lombok.SneakyThrows;

import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;

@Configuration
public class OrderConfiguration {

	@Autowired
	private LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction;

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	WebClient webClientCustomer() {
		return WebClient.builder().baseUrl("http://CUSTOMER-SERVICE").filter(loadBalancedExchangeFilterFunction)
				.build();
	}

	@Bean
	WebClient webClientProduct() {
		return WebClient.builder().baseUrl("http://PRODUCT-SERVICE").filter(loadBalancedExchangeFilterFunction).build();
	}

	@SneakyThrows
	@Bean
	CustomerClient customerClient() {
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builderFor(WebClientAdapter.create(webClientCustomer())).build();
		return factory.createClient(CustomerClient.class);
	}

	@SneakyThrows
	@Bean
	ProductClient productClient() {
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builderFor(WebClientAdapter.create(webClientProduct())).build();
		return factory.createClient(ProductClient.class);
	}

}
