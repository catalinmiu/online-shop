package com.example.onlineShop.domain;

import java.util.Objects;

public class CartProduct {

    private int id;

    private int product_id;

    private int units;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartProduct that = (CartProduct) o;
        return id == that.id &&
                product_id == that.product_id &&
                units == that.units;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product_id, units);
    }
}
