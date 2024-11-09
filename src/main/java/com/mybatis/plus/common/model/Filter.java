package com.mybatis.plus.common.model;

import java.util.List;

public class Filter {
    private String joinSymbol = "and";
    private ListableFilter listableFilter;
    private CombinedListableFilter combinedListableFilter;
    private List<SortField> sortFields;

    public String getJoinSymbol() {
        return joinSymbol;
    }

    public void setJoinSymbol(String joinSymbol) {
        this.joinSymbol = joinSymbol;
    }

    public ListableFilter getListableFilter() {
        return listableFilter;
    }

    public void setListableFilter(ListableFilter listableFilter) {
        this.listableFilter = listableFilter;
    }

    public CombinedListableFilter getCombinedListableFilter() {
        return combinedListableFilter;
    }

    public void setCombinedListableFilter(CombinedListableFilter combinedListableFilter) {
        this.combinedListableFilter = combinedListableFilter;
    }

    public List<SortField> getSortFields() {
        return sortFields;
    }

    public void setSortFields(List<SortField> sortFields) {
        this.sortFields = sortFields;
    }
}
