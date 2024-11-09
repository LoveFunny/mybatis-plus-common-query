package com.mybatis.plus.common.model;

public class BasicFilter {
    private String field;
    private String symbol = "=";
    private Object value;
    private String valueType = "String";

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}
