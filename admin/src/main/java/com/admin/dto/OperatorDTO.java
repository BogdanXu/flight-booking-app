package com.admin.dto;

public class OperatorDTO extends OperatorBaseDTO{
    private String iban;
    public OperatorDTO() {
    }

    public OperatorDTO(long id, String name, String URI, String iban) {
        super(id, name, URI);
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }


}
