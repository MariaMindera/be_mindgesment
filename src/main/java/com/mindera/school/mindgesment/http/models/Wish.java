package com.mindera.school.mindgesment.http.models;

import javax.validation.constraints.*;
import java.time.YearMonth;
import java.util.Objects;

public class Wish {

    private String id;

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotNull
    @Positive
    private Long total;

    @Positive
    private Long proposalAmount;

    private Long balance = 0L;

    @FutureOrPresent
    private YearMonth date;

    @Size(max = 120)
    private String description;

    public Wish() {
    }

    public Wish(@NotBlank @Size(max = 30) String name, @NotNull @Positive Long total, @Positive Long proposalAmount) {
        this.name = name;
        this.total = total;
        this.proposalAmount = proposalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wish wish = (Wish) o;
        return Objects.equals(id, wish.id) &&
                Objects.equals(name, wish.name) &&
                Objects.equals(total, wish.total) &&
                Objects.equals(proposalAmount, wish.proposalAmount) &&
                Objects.equals(balance, wish.balance) &&
                Objects.equals(date, wish.date) &&
                Objects.equals(description, wish.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, total, proposalAmount, balance, date, description);
    }

    @Override
    public String toString() {
        return "Wish{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", total=" + total +
                ", proposalAmount=" + proposalAmount +
                ", balance=" + balance +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
