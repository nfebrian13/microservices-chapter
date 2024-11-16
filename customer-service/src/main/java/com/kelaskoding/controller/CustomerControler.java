package com.kelaskoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelaskoding.dto.SearchEmailRequest;
import com.kelaskoding.entity.Customer;
import com.kelaskoding.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
@RefreshScope
public class CustomerControler {

	@Value("${spring.application.version}")
	private String versionString;
	
	@Autowired
	private CustomerService customerService;

	@PostMapping
	public Customer save(@RequestBody Customer customer) {
		return customerService.save(customer);
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable("id") Long id) {
		return customerService.findById(id);
	}
	
	@GetMapping("/version")
	public String getVersionCustomer() {
		return versionString;
	}

	@GetMapping
	public Iterable<Customer> findAll() {
		return customerService.findAll();
	}

	@PostMapping("/search-by-email")
	public Customer findByEmail(@RequestBody SearchEmailRequest form) {
		return customerService.findByEmail(form.getEmail());
	}
}
