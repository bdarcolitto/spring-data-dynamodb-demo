package com.example.demo.vo;

public class ProductVO {

    private String id;
    private String name;
    private Float value;
    private Integer createDate;

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
