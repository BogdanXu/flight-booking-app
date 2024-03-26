package com.admin.dto;

public class OperatorDTO extends OperatorBaseDTO{
    private String IBAN;
    public OperatorDTO() {
    }

    public OperatorDTO(long id, String name, String URI, String IBAN) {
        super(id, name, URI);
        this.IBAN = IBAN;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }


}
