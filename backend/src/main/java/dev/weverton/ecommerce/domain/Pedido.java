package dev.weverton.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date instante;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_entrega_id")
    private Endereco enderecoEntrega;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido(Long id, Date instante, Cliente cliente, Endereco enderecoEntrega) {
        this.id = id;
        this.instante = instante;
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
    }

    public Double getValorTotal(){
        Double total = 0.0;
        for(ItemPedido item: itens){
            total += item.getSubTotal();
        }

        return total;
    }

    @Override
    public String toString() {
        String str = "Pedido nº: " + getId() +
                ", Instante: " + getInstante() +
                ", Cliente: " + getCliente().getNome() +
                ", Situação: " + getPagamento().getStatus().getDescricao() +
                ", Detalhes: {";

        for(ItemPedido i: getItens()){
            str += i.toString();
        }

        str += "}, Valor total: " + getValorTotal();
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
