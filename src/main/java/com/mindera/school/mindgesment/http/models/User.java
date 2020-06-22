package com.mindera.school.mindgesment.http.models;

import java.util.List;
import java.util.Objects;

public class User {

    private String id;

    private String email;

    private String username;

    private Coin coin;

    private Double earnings;

    private Double expenses;

    private Integer monthlyPeriod;

    private List<String> role;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public Double getEarnings() {
        return earnings;
    }

    public void setEarnings(Double earnings) {
        this.earnings = earnings;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public Integer getMonthlyPeriod() {
        return monthlyPeriod;
    }

    public void setMonthlyPeriod(Integer monthlyPeriod) {
        this.monthlyPeriod = monthlyPeriod;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(username, user.username) &&
                coin == user.coin &&
                Objects.equals(earnings, user.earnings) &&
                Objects.equals(expenses, user.expenses) &&
                Objects.equals(monthlyPeriod, user.monthlyPeriod) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, coin, earnings, expenses, monthlyPeriod, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", coin=" + coin +
                ", earnings=" + earnings +
                ", expenses=" + expenses +
                ", monthlyPeriod=" + monthlyPeriod +
                ", role=" + role +
                '}';
    }
}
