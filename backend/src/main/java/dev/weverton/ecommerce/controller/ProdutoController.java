package dev.weverton.ecommerce.controller;

import dev.weverton.ecommerce.controller.utils.URL;
import dev.weverton.ecommerce.domain.Produto;
import dev.weverton.ecommerce.dto.ProdutoDTO;
import dev.weverton.ecommerce.services.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id){
        Produto produto = produtoService.find(id);
        return ResponseEntity.ok().body(produto);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        List<ProdutoDTO> produtos = produtoService.findAll();
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProdutoDTO>> search(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "rows", defaultValue = "24", required = false) Integer rows,
            @RequestParam(value = "orderBy", defaultValue = "nome", required = false) String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC", required = false) String direction
    ){
        Page<ProdutoDTO> pageList = produtoService.search(
                URL.decodeStringParam(nome), URL.decodeLongList(categorias), page, rows, orderBy, direction
        );

        return ResponseEntity.ok().body(pageList);
    }

}
