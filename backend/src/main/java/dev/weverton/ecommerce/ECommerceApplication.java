package dev.weverton.ecommerce;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import dev.weverton.ecommerce.domain.*;
import dev.weverton.ecommerce.domain.enums.PagamentoStatus;
import dev.weverton.ecommerce.domain.enums.TipoCliente;
import dev.weverton.ecommerce.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}
}
