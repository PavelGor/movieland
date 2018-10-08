package com.gordeev.movieland.vo;

public class RequestParameter {
    private String fieldName;
    private SortDirection sortDirection;

    public RequestParameter(String fieldName, SortDirection sortDirection) {
        this.fieldName = fieldName;
        this.sortDirection = sortDirection;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }
}
