package com.example.onlineShop.domain.dto;

import java.util.Objects;


public class CartDto {

    private int id;

    private String title;

    private float price;

    private int stock;

    private int units;

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDto that = (CartDto) o;
        return id == that.id &&
                Float.compare(that.price, price) == 0 &&
                stock == that.stock &&
                units == that.units &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, stock, units);
    }
}
