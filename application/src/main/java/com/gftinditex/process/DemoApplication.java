package com.gftinditex.process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	//TODO: Cambiar nombres a la ejecución del plugin al generar código de servidor para que se llame application en lugar de adapter :/
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
