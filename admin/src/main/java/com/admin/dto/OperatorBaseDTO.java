package com.admin.dto;

public class OperatorBaseDTO {
    private long id;
    private String name;
    private String URI;

    public OperatorBaseDTO() {
    }

    public OperatorBaseDTO(long id, String name, String URI) {
        this.id = id;
        this.name = name;
        this.URI = URI;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
