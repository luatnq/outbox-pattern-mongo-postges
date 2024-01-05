package com.lawman.outbox.product.repository;

import com.lawman.outbox.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  void removeProductByMongoId(String mongoId);

  Product findByMongoId(String mongoId);
}
