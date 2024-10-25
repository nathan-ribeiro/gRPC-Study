package com.communication.gRPC.server;

import com.communication.gRPC.service.ProductServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class GrpcServer {

    private final int PORT = 50059;
    private Server server;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @PostConstruct
    public void init() throws IOException {
        // Executar o servidor gRPC em uma nova thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                start();
                blockUntilShutdown();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void start() throws IOException {
        // Criação do servidor gRPC e registro do serviço
        server = ServerBuilder.forPort(PORT)
                .addService(productServiceImpl) // Use a instância injetada
                .build()
                .start();

        System.out.println("Servidor gRPC iniciado na porta " + PORT);

        // Adiciona um hook para parar o servidor quando a aplicação for encerrada
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                GrpcServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
