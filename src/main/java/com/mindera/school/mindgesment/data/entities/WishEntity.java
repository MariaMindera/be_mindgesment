package com.mindera.school.mindgesment.data.entities;

import javax.persistence.*;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

/**
 * WishEntity class exists to save wish information.
 */
@Entity
@Table(name = "wishes")
public class WishEntity extends KeyEntity {

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Long total;

    @Column(nullable = false)
    private Long proposalAmount;

    @Column(nullable = false)
    private YearMonth date;

    @Column(length = 120)
    private String description;

    @Column(nullable = false)
    private Instant createdIn = Instant.now();

    private boolean completed = false;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "wish")
    private List<TransactionEntity> transactions;

    public WishEntity() {
    }

    public WishEntity(String name, Long total, Long proposalAmount, YearMonth date, UserEntity user) {
        this.name = name;
        this.total = total;
        this.proposalAmount = proposalAmount;
        this.date = date;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getProposalAmount() {
        return proposalAmount;
    }

    public void setProposalAmount(Long proposalAmount) {
        this.proposalAmount = proposalAmount;
    }

    public YearMonth getDate() {
        return date;
    }

    public void setDate(YearMonth date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(Instant createdIn) {
        this.createdIn = createdIn;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (! super.equals(o)) return false;
        WishEntity that = (WishEntity) o;
        return completed == that.completed &&
                Objects.equals(name, that.name) &&
                Objects.equals(total, that.total) &&
                Objects.equals(proposalAmount, that.proposalAmount) &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description) &&
                Objects.equals(createdIn, that.createdIn) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, total, proposalAmount, date, description, createdIn, completed, user, transactions);
    }

    @Override
    public String toString() {
        return "WishEntity{" +
                "name='" + name + '\'' +
                ", total=" + total +
                ", proposalAmount=" + proposalAmount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", createdIn=" + createdIn +
                ", completed=" + completed +
                ", user=" + user +
                '}';
    }
}
