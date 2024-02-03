package dev.weverton.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.weverton.ecommerce.domain.enums.PagamentoStatus;

import javax.persistence.Entity;

@Entity
@JsonTypeName("PAGAMENTO_CARTAO")
public class PagamentoCartao extends Pagamento{
    private Integer parcelas;

    public PagamentoCartao() {
    }

    public PagamentoCartao(Long id, PagamentoStatus status, Pedido pedido, Integer parcelas) {
        super(id, status, pedido);
        this.parcelas = parcelas;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }
}
