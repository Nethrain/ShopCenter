package dev.weverton.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.weverton.ecommerce.domain.enums.PagamentoStatus;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@JsonTypeName("PAGAMENTO_BOLETO")
public class PagamentoBoleto extends Pagamento{
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPagamento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataVencimento;

    public PagamentoBoleto(){}

    public PagamentoBoleto(Long id, PagamentoStatus status, Pedido pedido, Date dataPagamento, Date dataVencimento) {
        super(id, status, pedido);
        this.dataPagamento = dataPagamento;
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
