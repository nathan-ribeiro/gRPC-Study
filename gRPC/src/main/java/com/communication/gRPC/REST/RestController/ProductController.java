package com.communication.gRPC.REST.RestController;

import com.communication.gRPC.REST.Entity.Product;
import com.communication.gRPC.REST.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PostMapping(value = "/list")
    public String createProducts(@RequestBody Product product) {
        List<Product> productsToSave = new ArrayList<>();

        for (int i = 0; i < 15000; i++) {
            Product newProduct = new Product();

            String url = "http://localhost:1080/api/test";
            String response = restTemplate.getForObject(url, String.class);

            newProduct.setName(response);
            newProduct.setQuantity(product.getQuantity() + i);
            newProduct.setPrice(product.getPrice());
            productsToSave.add(newProduct);
        }

        productRepository.saveAll(productsToSave); // Salva todos os produtos de uma vez

        return "1000 novos produtos foram salvos!";
    }

    @DeleteMapping
    public String deleteAll(){
        productRepository.deleteAll();
        return "Todos os produtos foram deletados!";
    }

}
