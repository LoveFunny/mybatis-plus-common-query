package com.mybatis.plus.common.model;

import java.util.List;

public class ListableFilter {
    private String joinSymbol = "and";
    private List<BasicFilter> basicFilters;

    public List<BasicFilter> getBasicFilters() {
        return basicFilters;
    }

    public void setBasicFilters(List<BasicFilter> basicFilters) {
        this.basicFilters = basicFilters;
    }

    public String getJoinSymbol() {
        return joinSymbol;
    }

    public void setJoinSymbol(String joinSymbol) {
        this.joinSymbol = joinSymbol;
    }
}
