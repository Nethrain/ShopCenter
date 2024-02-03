package dev.weverton.ecommerce.dto;

import dev.weverton.ecommerce.domain.Cidade;
import dev.weverton.ecommerce.domain.Cliente;
import dev.weverton.ecommerce.domain.Endereco;
import dev.weverton.ecommerce.domain.enums.TipoCliente;
import dev.weverton.ecommerce.services.validation.ClienteStoreValidation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@ClienteStoreValidation
public class ClienteStoreDTO implements BaseDTO<Cliente> {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 3, max = 150, message = "O tamanho deve ser entre 3 e 150 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String documento;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String senha;

    private Integer tipo;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String logradouro;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String numero;

    private String complemento;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String bairro;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String cep;

    @NotEmpty(message = "Preenchimento obrigatório")
    private List<String> telefones = new ArrayList<>();

    private Integer cidadeId;

    public ClienteStoreDTO(String nome, String email, String documento, Integer tipo, String logradouro,
                           String numero, String complemento, String bairro, String cep,
                           List<String> telefones, Integer cidadeId, String senha) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.tipo = tipo;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.telefones = telefones;
        this.cidadeId = cidadeId;
        this.senha = senha;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }

    public Integer getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Integer cidadeId) {
        this.cidadeId = cidadeId;
    }


    @Override
    public Cliente toEntity() {
        Cliente cliente = new Cliente(null, nome, email, documento, TipoCliente.toEnum(tipo), encoder.encode(senha));
        Cidade cidade = new Cidade(cidadeId, null, null);
        Endereco endereco = new Endereco(null, logradouro, numero, complemento, bairro, cep, cliente, cidade);

        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().addAll(telefones);

        return cliente;
    }
}
