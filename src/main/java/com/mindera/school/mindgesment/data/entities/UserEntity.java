package com.mindera.school.mindgesment.data.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * UserEntity class exists to represent an user.
 */
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        })
public class UserEntity extends KeyEntity {

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private CoinEntity coin;

    @Column(scale = 2)
    @Min(1)
    @Max(31)
    private Integer monthlyPeriod = 1;

    @Column(scale = 2)
    private Double earnings = 0.00;

    @Column(scale = 2)
    private Double expenses = 0.00;

    private boolean isEnabled = true;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<RoleEntity> roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<TransactionEntity> transactions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<WishEntity> wishes;

    public UserEntity() {
    }

    public UserEntity(@Email String email, String password, String username, CoinEntity coin) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.coin = coin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public CoinEntity getCoin() {
        return coin;
    }

    public void setCoin(CoinEntity coin) {
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

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public List<WishEntity> getWishes() {
        return wishes;
    }

    public void setWishes(List<WishEntity> wishes) {
        this.wishes = wishes;
    }

    public Integer getMonthlyPeriod() {
        return monthlyPeriod;
    }

    public void setMonthlyPeriod(Integer monthlyPeriod) {
        this.monthlyPeriod = monthlyPeriod;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (! super.equals(o)) return false;
        UserEntity that = (UserEntity) o;
        return isEnabled == that.isEnabled &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(username, that.username) &&
                coin == that.coin &&
                Objects.equals(monthlyPeriod, that.monthlyPeriod) &&
                Objects.equals(earnings, that.earnings) &&
                Objects.equals(expenses, that.expenses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, username, coin, monthlyPeriod, earnings, expenses, isEnabled, roles, transactions, wishes);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "email='" + email + '\'' +
                ", password='" + "********" + '\'' +
                ", username='" + username + '\'' +
                ", coin=" + coin +
                ", monthlyPeriod=" + monthlyPeriod +
                ", earnings=" + earnings +
                ", expenses=" + expenses +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
