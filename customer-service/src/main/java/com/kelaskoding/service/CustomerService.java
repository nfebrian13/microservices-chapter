package com.kelaskoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelaskoding.entity.Customer;
import com.kelaskoding.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer findById(Long id) {
		return customerRepository.findById(id).orElse(null);
	}

	public Iterable<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
}
