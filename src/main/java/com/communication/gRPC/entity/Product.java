package com.communication.gRPC.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private Double price;
    private Long quantity;

}
