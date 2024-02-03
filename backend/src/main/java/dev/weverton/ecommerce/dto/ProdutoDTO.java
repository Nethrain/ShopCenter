package dev.weverton.ecommerce.dto;

import dev.weverton.ecommerce.domain.Categoria;
import dev.weverton.ecommerce.domain.Produto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class ProdutoDTO implements BaseDTO<Produto> {

    private Long id;
    private String nome;
    private Double valor;

    public ProdutoDTO(){}

    public ProdutoDTO(Produto produto){
        id = produto.getId();
        nome = produto.getNome();
        valor = produto.getValor();
    }

    public ProdutoDTO(Long id, String nome, Double valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public Produto toEntity() {
        return new Produto(id, nome, valor);
    }
}
