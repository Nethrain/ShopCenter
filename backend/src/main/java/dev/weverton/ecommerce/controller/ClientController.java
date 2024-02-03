package dev.weverton.ecommerce.controller;

import dev.weverton.ecommerce.domain.Cliente;
import dev.weverton.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClienteRepository clientRepository;

    @Autowired
    public ClientController(ClienteRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getClientById(@PathVariable Long id) {
        Cliente client = clientRepository.findById(id).orElse(null);

        if (client != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ClientResponse(client));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found with id: " + id);
        }
    }

    static class ClientResponse {
        private final Long id;
        private final String name;
        private final String email;
        private final String cpf;

        public ClientResponse(Cliente client) {
            this.id = client.getId();
            this.name = client.getName();
            this.email = client.getEmail();
            this.cpf = client.getCpf();
        }


        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getCpf() {
            return cpf;
        }
    }
}
