package model;

import java.io.IOException;

public class Pagination {
    private int total; // total amount of articles
    private int perPage; // amount off articles per page
    private int page;
    private int next;
    private int last;
    private int current;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return  "total" + ":" + total +
                ", perPage=" + ":" + perPage +
                ", page=" + ":" + page +
                ", next=" + ":" + next +
                ", last=" + ":" + last +
                '}';
    }
}
