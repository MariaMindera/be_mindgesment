package com.mindera.school.mindgesment.http.models;

import java.util.List;
import java.util.Objects;

public class PaginatedTransactions {

    private List<Transaction> list;

    private Long total;

    private Long totalPerPage = 10L;

    public PaginatedTransactions() {
    }

    public PaginatedTransactions(List<Transaction> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public List<Transaction> getList() {
        return list;
    }

    public void setList(List<Transaction> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalPerPage() {
        return totalPerPage;
    }

    public void setTotalPerPage(Long totalPerPage) {
        this.totalPerPage = totalPerPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaginatedTransactions that = (PaginatedTransactions) o;
        return Objects.equals(list, that.list) &&
                Objects.equals(total, that.total) &&
                Objects.equals(totalPerPage, that.totalPerPage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, total, totalPerPage);
    }

    @Override
    public String toString() {
        return "PaginatedTransactions{" +
                "list=" + list +
                ", total=" + total +
                ", totalPerPage=" + totalPerPage +
                '}';
    }
}
