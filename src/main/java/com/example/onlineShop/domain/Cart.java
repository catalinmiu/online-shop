package com.example.onlineShop.domain;

import java.util.Objects;

public class Cart {

    private int id;

    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id == cart.id &&
                user_id == cart.user_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id);
    }
}
