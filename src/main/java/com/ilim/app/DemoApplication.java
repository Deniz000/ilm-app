package com.ilim.app;

import com.ilim.app.core.exceptions.BusinessExceptions;
import com.ilim.app.core.exceptions.ProblemDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SpringBootApplication
@RestControllerAdvice //hata yönetimini kurgulamayı sağlayan/destekleyen yapı
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ProblemDetails handleBusinessExceptions(BusinessExceptions businessExceptions){
		//hangi hata olursa çalışsın? parametrede veriyosun
		ProblemDetails problemDetails = new ProblemDetails();
		problemDetails.setMessage(businessExceptions.getMessage());
		return problemDetails;
	}
}
