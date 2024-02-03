package dev.weverton.ecommerce.controller;

import dev.weverton.ecommerce.domain.Cliente;
import dev.weverton.ecommerce.domain.Cliente;
import dev.weverton.ecommerce.domain.Cliente;
import dev.weverton.ecommerce.dto.ClienteDTO;
import dev.weverton.ecommerce.dto.ClienteDTO;
import dev.weverton.ecommerce.dto.ClienteStoreDTO;
import dev.weverton.ecommerce.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll(){
        List<ClienteDTO> clientes = clienteService.findAll();
        return ResponseEntity.ok().body(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> find(@PathVariable Long id){
        Cliente cliente = clienteService.find(id);
        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping
    public ResponseEntity<Void> store(@Valid @RequestBody ClienteStoreDTO clienteStoreDTO){
        Cliente cliente = clienteService.store(clienteStoreDTO.toEntity());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Long id){
        clienteDTO.setId(id);
        Cliente cliente = clienteService.update(clienteDTO.toEntity());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
