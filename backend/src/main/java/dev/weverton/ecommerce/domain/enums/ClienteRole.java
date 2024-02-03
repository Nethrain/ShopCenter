package dev.weverton.ecommerce.domain.enums;

public enum ClienteRole {
    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private Integer cod;
    private String descricao;

    ClienteRole(Integer cod, String descricao){
        this.cod = cod;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public static ClienteRole toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for(ClienteRole p : ClienteRole.values()){
            if(cod.equals(p.getCod())){
                return p;
            }
        }

        throw new IllegalArgumentException("ClienteRole(" + cod +") inválido!");
    }
}
