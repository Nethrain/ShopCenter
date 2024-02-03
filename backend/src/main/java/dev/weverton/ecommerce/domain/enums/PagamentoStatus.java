package dev.weverton.ecommerce.domain.enums;

public enum PagamentoStatus {
    PENDENTE(1, "Pedente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private Integer cod;
    private String descricao;

    PagamentoStatus(Integer cod, String descricao){
        this.cod = cod;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public static PagamentoStatus toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for(PagamentoStatus p : PagamentoStatus.values()){
            if(cod.equals(p.getCod())){
                return p;
            }
        }

        throw new IllegalArgumentException("PagamentoStatus(" + cod +") inválido!");
    }
}
