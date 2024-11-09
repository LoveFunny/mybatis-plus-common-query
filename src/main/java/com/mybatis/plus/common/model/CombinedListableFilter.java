package com.mybatis.plus.common.model;

import java.util.List;

public class CombinedListableFilter {
    private String joinSymbol;

    private List<ListableFilter> listableFilters;

    public String getJoinSymbol() {
        return joinSymbol;
    }

    public void setJoinSymbol(String joinSymbol) {
        this.joinSymbol = joinSymbol;
    }

    public List<ListableFilter> getListableFilters() {
        return listableFilters;
    }

    public void setListableFilters(List<ListableFilter> listableFilters) {
        this.listableFilters = listableFilters;
    }
}
