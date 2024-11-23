package com.kelaskoding.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kelaskoding.dto.Customer;
import com.kelaskoding.dto.OrderLineResponse;
import com.kelaskoding.dto.OrderResponse;
import com.kelaskoding.dto.Product;
import com.kelaskoding.entity.Order;
import com.kelaskoding.entity.OrderLine;
import com.kelaskoding.repository.OrderRepository;
import com.kelaskoding.webclient.CustomerClient;
import com.kelaskoding.webclient.ProductClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CustomerClient customerClient;
	
	@Autowired
	private ProductClient productClient;

	public Order save(Order order) {
		for (OrderLine orderLine : order.getOrderLines()) {
			orderLine.setOrder(order);
		}
		return orderRepository.save(order);
	}

	public Order findById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

	public Iterable<Order> findAll() {
		return orderRepository.findAll();
	}

	public void deleteById(Long id) {
		orderRepository.deleteById(id);
	}

	@CircuitBreaker(name="customerService", fallbackMethod = "fallbackFindCustomerById")
	public OrderResponse findByOrderId(Long id) {
		Optional<Order> optOrder = orderRepository.findById(id);
		if (!optOrder.isPresent()) {
			return null;
		}

		Order order = optOrder.get();
		OrderResponse orderResponse = new OrderResponse(order.getId(), order.getOrderNumber(), order.getOrderDate(),
//				findCustomerById(order.getCustomerId()), new ArrayList<OrderLineResponse>());
				customerClient.findCustomerById(order.getCustomerId()), new ArrayList<OrderLineResponse>());

		for (OrderLine orderLine : order.getOrderLines()) {
//			Product product = findProductById(orderLine.getProductId());
			Product product = productClient.findProductById(orderLine.getProductId());
			orderResponse.getOrderLines()
					.add(new OrderLineResponse(order.getId(), product, orderLine.getQuantity(), orderLine.getPrice()));

		}

		return orderResponse;
	}

	public OrderResponse findByOrderNumber(String orderNumber) {
		Order order = orderRepository.findByOrderNumber(orderNumber);
		if (order == null) {
			return null;
		}

		OrderResponse orderResponse = new OrderResponse(order.getId(), order.getOrderNumber(), order.getOrderDate(),
//				findCustomerById(order.getCustomerId()), new ArrayList<OrderLineResponse>());
				customerClient.findCustomerById(order.getCustomerId()), new ArrayList<OrderLineResponse>());

		for (OrderLine orderLine : order.getOrderLines()) {
//			Product product = findProductById(orderLine.getProductId());
			Product product =  productClient.findProductById(orderLine.getProductId());
			orderResponse.getOrderLines()
					.add(new OrderLineResponse(order.getId(), product, orderLine.getQuantity(), orderLine.getPrice()));
		}

		return orderResponse;
	}

	public Customer findCustomerById(Long id) {
//		return restTemplate.getForObject("http://localhost:8081/api/customers/" + id, Customer.class);
		return restTemplate.getForObject("http://CUSTOMER-SERVICE/api/customers/" + id, Customer.class);
	}

	public Product findProductById(Long id) {
//		return restTemplate.getForObject("http://localhost:8082/api/products/" + id, Product.class);
		return restTemplate.getForObject("http://PRODUCT-SERVICE/api/products/" + id, Product.class);
	}
	
	private OrderResponse fallbackFindCustomerById(Long id, Throwable throwable) {
		return new OrderResponse();
	}
	
}
