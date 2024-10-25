package com.communication.gRPC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.communication.gRPC")
public class GRpcApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GRpcApplication.class, args);
	}

}
