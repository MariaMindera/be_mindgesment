package com.mindera.school.mindgesment.http.models;

import java.util.List;
import java.util.Objects;

public class PaginatedWishes {

    private List<Wish> list;

    private List<Wish> completed;

    private Long total;

    private Long totalPerPage = 10L;

    public PaginatedWishes() {
    }

    public List<Wish> getList() {
        return list;
    }

    public void setList(List<Wish> list) {
        this.list = list;
    }

    public List<Wish> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Wish> completed) {
        this.completed = completed;
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
        PaginatedWishes that = (PaginatedWishes) o;
        return Objects.equals(list, that.list) &&
                Objects.equals(completed, that.completed) &&
                Objects.equals(total, that.total) &&
                Objects.equals(totalPerPage, that.totalPerPage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, completed, total, totalPerPage);
    }

    @Override
    public String toString() {
        return "PaginatedWishes{" +
                "list=" + list +
                ", completed=" + completed +
                ", total=" + total +
                ", totalPerPage=" + totalPerPage +
                '}';
    }
}
