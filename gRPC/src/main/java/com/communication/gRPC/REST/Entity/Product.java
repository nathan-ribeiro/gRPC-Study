package com.communication.gRPC.REST.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "ProductEntity")
@Table(name = "TB_PRODUCT")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "QUANTITY")
    private Long quantity;

}
