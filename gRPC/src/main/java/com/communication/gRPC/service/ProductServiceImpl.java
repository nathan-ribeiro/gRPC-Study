package com.communication.gRPC.service;

import com.communication.gRPC.REST.Entity.Product;
import com.communication.gRPC.REST.RestController.ProductController;
import com.communication.gRPC.REST.repository.ProductRepository;
import com.communication.gRPC.gRPC.service.NameServiceGrpc;
import com.communication.gRPC.gRPC.service.NameServiceProto;
import com.communication.gRPC.gRPC.service.NameServiceProto.NameRequest;
import com.communication.gRPC.gRPC.service.NameServiceProto.NameResponse;
import com.communication.gRPC.gRPC.service.ProductProto.ProductRequest;
import com.communication.gRPC.gRPC.service.ProductProto.ProductResponse;
import com.communication.gRPC.gRPC.service.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    public void saveProduct(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {

        // Salvando produto no banco
        productRepository.save(Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(Long.valueOf(request.getQuantity()))
                .build());

        ProductResponse resposta = ProductResponse.newBuilder()
                .setMessage("Produto salvo com sucesso!")
                .build();

        // Enviamos a resposta e completamos a comunicação
        responseObserver.onNext(resposta);
        responseObserver.onCompleted();
    }

    @Override
    public void saveProducts(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {

        List<Product> productsToSave = new ArrayList<>();

        for (int i = 0; i < 15000; i++) {

            //FLUXO DE CHAMAR OUTRO gRPC - START
            NameServiceGrpc.NameServiceBlockingStub nameServiceStub;
            ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                    .usePlaintext()  // Desabilita SSL
                    .build();
            nameServiceStub = NameServiceGrpc.newBlockingStub(channel);

            // Criando requisição vazia
            NameRequest nameRequest = NameRequest.newBuilder().build();

            // Chamando Servidor gRPC e pegando a resposta
            NameResponse response = nameServiceStub.getName(nameRequest);
            //FLUXO DE CHAMAR OUTRO gRPC - END

            Product newProduct = new Product();
            newProduct.setName(response.getName());
            newProduct.setQuantity(Long.valueOf(request.getQuantity() + i));
            newProduct.setPrice(request.getPrice());
            productsToSave.add(newProduct);
        }

        productRepository.saveAll(productsToSave);

        ProductResponse resposta = ProductResponse.newBuilder()
                .setMessage("Produto salvo com sucesso!")
                .build();

        // Enviamos a resposta e completamos a comunicação
        responseObserver.onNext(resposta);
        responseObserver.onCompleted();
    }

}

