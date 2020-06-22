package com.mindera.school.mindgesment.http.models;

import javax.validation.constraints.*;
import java.util.Objects;

public class UserRegister {

    @Email
    @NotBlank
    private String email;

    @Size(min = 8)
    @NotBlank
    private String password;

    @Size(max = 50)
    @NotBlank
    private String username;

    @NotNull
    private String coin;

    @Digits(integer = 9, fraction = 2)
    private Double earnings;

    @Digits(integer = 9, fraction = 2)
    private Double expenses;

    public UserRegister() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRegister)) return false;
        UserRegister that = (UserRegister) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(username, that.username) &&
                coin.equals(that.coin) &&
                Objects.equals(earnings, that.earnings) &&
                Objects.equals(expenses, that.expenses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, username, coin, earnings, expenses);
    }

    @Override
    public String toString() {
        return "UserRegister{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", coin=" + coin +
                ", earnings=" + earnings +
                ", expenses=" + expenses +
                '}';
    }
}
