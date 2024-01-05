package com.lawman.outbox.product.service;

import com.lawman.outbox.product.entity.Product;

public interface ProductService {
  void handleEvent(String operation, String documentId, String collection, Product product);
}
