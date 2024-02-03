package dev.weverton.ecommerce.controller;

import dev.weverton.ecommerce.domain.Categoria;
import dev.weverton.ecommerce.domain.Pedido;
import dev.weverton.ecommerce.dto.CategoriaDTO;
import dev.weverton.ecommerce.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id){
        Pedido pedido = pedidoService.find(id);
        return ResponseEntity.ok().body(pedido);
    }

    @PostMapping
    public ResponseEntity<Void> store(@RequestBody Pedido pedidoObj){
        pedidoObj = pedidoService.store(pedidoObj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pedidoObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
