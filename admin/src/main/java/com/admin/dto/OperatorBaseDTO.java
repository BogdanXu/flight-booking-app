package com.admin.dto;

public class OperatorBaseDTO {
    private long id;
    private String name;
    private String uri;

    public OperatorBaseDTO() {
    }

    public OperatorBaseDTO(long id, String name, String uri) {
        this.id = id;
        this.name = name;
        this.uri = uri;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
