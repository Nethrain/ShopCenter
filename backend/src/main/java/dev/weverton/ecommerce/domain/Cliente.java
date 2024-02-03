package dev.weverton.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.weverton.ecommerce.domain.enums.ClienteRole;
import dev.weverton.ecommerce.domain.enums.TipoCliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "cliente")
@Getter
@Setter
public class Cliente {

    @Getter
    private String name;
    @Getter
    private String cpf;

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Column(unique = true)
    private String email;
    private String documento;

    @JsonIgnore
    private String senha;

    private Integer tipo;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL )
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "telefone")
    private Set<String> telefones = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="roles")
    private Set<Integer> roles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente(){
        addRole(ClienteRole.CLIENTE);
    }

    public Cliente(Long id, String nome, String email, String documento, TipoCliente tipo, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.tipo = tipo == null ? null : tipo.getCod();
        this.senha = senha;
        addRole(ClienteRole.CLIENTE);
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo.getCod();
    }
    public TipoCliente getTipo() {
        return TipoCliente.toEnum(tipo);
    }

    public Set<ClienteRole> getRoles(){
        return roles.stream().map(role -> ClienteRole.toEnum(role)).collect(Collectors.toSet());
    }

    public void addRole(ClienteRole role){
        roles.add(role.getCod());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
