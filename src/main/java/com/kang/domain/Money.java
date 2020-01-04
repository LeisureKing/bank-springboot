package com.kang.domain;

import java.util.Objects;

public class Money {
    private int userId;
    private double money;

    public Money() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money1 = (Money) o;
        return userId == money1.userId &&
                Double.compare(money1.money, money) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, money);
    }

    @Override
    public String toString() {
        return "money{" +
                "userId=" + userId +
                ", money=" + money +
                '}';
    }
}
