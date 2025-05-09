package com.npro.UserManagementService.payload;

import java.util.List;

public class UserPage {

    private List<UserResponse> budgetList;
    private boolean firstPage;
    private Integer totalPages;
    private Integer currentPage;
    private Integer totalElements;
    private Integer size;
    private Integer total;
    private boolean lastPage;

    public UserPage(List<UserResponse> budgetList, boolean firstPage, Integer totalPages, Integer currentPage, Integer totalElements, Integer size, Integer total, boolean lastPage) {
        this.budgetList = budgetList;
        this.firstPage = firstPage;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.size = size;
        this.total = total;
        this.lastPage = lastPage;
    }

    public UserPage() {
    }

    public List<UserResponse> getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(List<UserResponse> budgetList) {
        this.budgetList = budgetList;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }
}
