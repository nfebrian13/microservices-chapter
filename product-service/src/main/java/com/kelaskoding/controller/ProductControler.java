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

import com.kelaskoding.entity.Product;
import com.kelaskoding.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RefreshScope
public class ProductControler {

	@Value("${spring.application.version}")
	private String versionString;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/version")
	public String getVersionProduct() {
		return versionString;
	}
	
	@PostMapping
	public Product save(@RequestBody Product product) {
		return productService.save(product);
	}

	@GetMapping("/{id}")
	public Product findById(@PathVariable("id") Long id) {
		return productService.findById(id);
	}

	@GetMapping
	public Iterable<Product> findAll() {
		return productService.findAll();
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") Long id) {
		productService.deleteById(id);
	}
}
