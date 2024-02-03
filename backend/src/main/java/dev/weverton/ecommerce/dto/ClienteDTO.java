package dev.weverton.ecommerce.dto;


import dev.weverton.ecommerce.domain.Cliente;
import dev.weverton.ecommerce.services.validation.ClienteUpdateValidation;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ClienteUpdateValidation
public class ClienteDTO implements BaseDTO<Cliente> {
    private Long id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 3, max = 150, message = "O tamanho deve ser entre 3 e 150 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente){
        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
    }

    public ClienteDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cliente toEntity() {
        return new Cliente(id, nome, email, null, null, null);
    }
}
