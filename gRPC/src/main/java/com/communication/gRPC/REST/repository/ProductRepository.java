package com.communication.gRPC.REST.repository;

import com.communication.gRPC.REST.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
