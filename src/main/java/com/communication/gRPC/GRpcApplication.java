package com.communication.gRPC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GRpcApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GRpcApplication.class, args);
	}

}
