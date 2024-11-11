package com.kelaskoding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelaskoding.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
