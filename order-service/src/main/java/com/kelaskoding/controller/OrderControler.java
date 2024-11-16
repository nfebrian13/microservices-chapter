package com.kelaskoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelaskoding.dto.OrderResponse;
import com.kelaskoding.entity.Order;
import com.kelaskoding.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RefreshScope
public class OrderControler {

	@Value("${spring.application.version}")
	private String versionString;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/version")
	public String getVersionOrder() {
		return versionString;
	}

	@PostMapping
	public Order save(@RequestBody Order order) {
		return orderService.save(order);
	}

	/*
	 * @GetMapping("/{id}") public Order findById(@PathVariable("id") Long id) {
	 * return orderService.findById(id); }
	 */

	@GetMapping("/{id}")
	public OrderResponse findByOrderId(@PathVariable("id") Long id) {
		return orderService.findByOrderId(id);
	}

	@GetMapping("/order-number/{number}")
	public OrderResponse findByOrderNumber(@PathVariable("number") String number) {
		return orderService.findByOrderNumber(number);
	}

	@GetMapping
	public Iterable<Order> findAll() {
		return orderService.findAll();
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") Long id) {
		orderService.deleteById(id);
	}
}
