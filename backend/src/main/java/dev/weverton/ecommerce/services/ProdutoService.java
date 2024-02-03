package dev.weverton.ecommerce.services;

import dev.weverton.ecommerce.domain.Categoria;
import dev.weverton.ecommerce.domain.Produto;
import dev.weverton.ecommerce.dto.ProdutoDTO;
import dev.weverton.ecommerce.exceptions.EntityNotFoundException;
import dev.weverton.ecommerce.repositories.CategoriaRepository;
import dev.weverton.ecommerce.repositories.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto find(Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(
                () -> new EntityNotFoundException("Produto(" + id +") não encontrado"));
   }

   public Page<ProdutoDTO> search(String nome, List<Long> ids, Integer page, Integer rows, String orderBy, String direction){
       PageRequest pageRequest = PageRequest.of(page, rows, Sort.Direction.valueOf(direction), orderBy);
       List<Categoria> categorias = categoriaRepository.findAllById(ids);

       return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest)
               .map(obj -> new ProdutoDTO(obj));
   }

   public List<ProdutoDTO> findAll() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(ProdutoDTO::new) // ProdutoDTO'ya dönüştürme işlemi
                .collect(Collectors.toList());
    }
}
