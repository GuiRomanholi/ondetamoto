package br.com.fiap.ondetamoto;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//http://localhost:8080/swagger-ui/index.html
@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "API OndeTaMoto", description = "API RESTful com Swagger para OndeTaMoto", version = "v1"))
public class OndetamotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OndetamotoApplication.class, args);
	}

}
