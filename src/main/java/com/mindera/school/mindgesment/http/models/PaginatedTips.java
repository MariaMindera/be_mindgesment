package com.mindera.school.mindgesment.http.models;

import java.util.List;
import java.util.Objects;

/**
 * PaginatedTips class exists to allow other custom exceptions to be created by implementing this class.
 * <p>
 * This project uses Exceptions to define http status code of the responses, thus this class ensures that all
 * exceptions that will be used to define the response status code will have a error code, message and a reason defined.
 */
public class PaginatedTips {

    private List<Tip> list;

    private Long total;

    private Long totalPerPage = 10L;

    public PaginatedTips() {
    }

    public List<Tip> getList() {
        return list;
    }

    public void setList(List<Tip> list) {
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
        PaginatedTips that = (PaginatedTips) o;
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
        return "PaginatedTips{" +
                "list=" + list +
                ", total=" + total +
                ", totalPerPage=" + totalPerPage +
                '}';
    }
}
