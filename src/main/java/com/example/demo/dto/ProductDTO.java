package com.example.demo.dto;

public class ProductDTO {

    private String id;
    private String name;
    private Float value;
    private Integer createDate;

    public ProductDTO () {
    }

    public ProductDTO (final String id, final String name, final Float value, final Integer createDate) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.createDate = createDate;
    }

    public String getId () {
        return id;
    }

    public void setId (final String id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (final String name) {
        this.name = name;
    }

    public Float getValue () {
        return value;
    }

    public void setValue (final Float value) {
        this.value = value;
    }

    public Integer getCreateDate () {
        return createDate;
    }

    public void setCreateDate (final Integer createDate) {
        this.createDate = createDate;
    }
}
