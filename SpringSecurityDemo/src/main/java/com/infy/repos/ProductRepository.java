package com.infy.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	

}
