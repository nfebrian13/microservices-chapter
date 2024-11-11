package com.kelaskoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kelaskoding.entity.Product;
import com.kelaskoding.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product save(Product product) {
		return productRepository.save(product);
	}

	public Product findById(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

}
