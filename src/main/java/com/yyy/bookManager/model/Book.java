package com.yyy.bookManager.model;

//model包里的都是实体类，前端显示需要什么我们就提供什么。model里的代码不仅可以被service调，
// 也可以被controllers调，但是通常controllers调service偏多

//该类描述书本的基本属性
public class Book {
    private int id;

    private String name;

    private String author;

    private String price;

    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
