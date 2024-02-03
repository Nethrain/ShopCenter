package dev.weverton.ecommerce.dto;

import dev.weverton.ecommerce.domain.Categoria;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CategoriaDTO implements BaseDTO<Categoria> {
    private Long id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 3, max = 80, message = "O tamanho deve ser entre 3 e 80 caracteres")
    private String nome;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CategoriaDTO(Categoria categoria){
        this.id = categoria.getId();
        this.nome = categoria.getNome();
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

    public Categoria toEntity(){
        return new Categoria(this.id, this.nome);
    }
}
