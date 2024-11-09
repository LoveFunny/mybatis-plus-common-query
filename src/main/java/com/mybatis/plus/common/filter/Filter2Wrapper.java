package com.mybatis.plus.common.filter;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mybatis.plus.common.model.BasicFilter;
import com.mybatis.plus.common.model.CombinedListableFilter;
import com.mybatis.plus.common.model.Filter;
import com.mybatis.plus.common.model.ListableFilter;
import com.mybatis.plus.common.model.SortField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Filter2Wrapper {
    private static final Map<Class, List<String>> ENTITY_FIELD_MAP = new HashMap<>();

    public static <T> Wrapper<T> filter2Wrapper(Class<T> clazz, Filter filter) {
        Wrapper<T> wrapper = null;
        if (filter != null) {
            validField(clazz, filter);
            String joinSymbol = filter.getJoinSymbol();
            ListableFilter listableFilter = filter.getListableFilter();
            if (listableFilter != null && listableFilter.getBasicFilters() != null) {
                wrapper = new EntityWrapper<>();
                setWrapperWithJoinSymbol(wrapper, joinSymbol, false);
                setWrapperWithListableFilter(wrapper, listableFilter);
            }
            CombinedListableFilter combinedListableFilter = filter.getCombinedListableFilter();
            if (combinedListableFilter != null && combinedListableFilter.getListableFilters() != null) {
                if (wrapper == null) {
                    wrapper = new EntityWrapper<>();
                    setWrapperWithJoinSymbol(wrapper, joinSymbol, false);
                } else {
                    setWrapperWithJoinSymbol(wrapper, joinSymbol, true);
                }
                List<ListableFilter> listableFilters = combinedListableFilter.getListableFilters();
                String combinedListableFilterJoinSymbol = combinedListableFilter.getJoinSymbol();
                for (int i = 0, total = listableFilters.size(); i < total; i++) {
                    setWrapperWithListableFilter(wrapper, listableFilters.get(i));
                    if (i != total - 1) {
                        setWrapperWithJoinSymbol(wrapper, combinedListableFilterJoinSymbol, true);
                    }
                }
            }
            if (wrapper != null && filter.getSortFields() != null) {
                for (SortField sortField : filter.getSortFields()) {
                    wrapper.orderBy(sortField.getField(), sortField.isAsc());
                }
            }
        }
        return wrapper;
    }

    private static <T> void validField(Class<T> clazz, Filter filter) {
        List<String> filterFieldList = new ArrayList<>();
        addField(filterFieldList, filter.getListableFilter());
        addField(filterFieldList, filter.getCombinedListableFilter());

        List<String> entityQueryableFields = ENTITY_FIELD_MAP.computeIfAbsent(clazz, (k) -> {
            Field[] fields = k.getDeclaredFields();
            List<String> queryableFieldList = new ArrayList<>();
            for (Field field : fields) {
                if (field.getAnnotation(TableField.class) != null) {
                    TableField tableField = field.getAnnotation(TableField.class);
                    queryableFieldList.add(tableField.value().toUpperCase(Locale.US));
                } else if (field.getAnnotation(TableId.class) != null) {
                    TableId tableId = field.getAnnotation(TableId.class);
                    queryableFieldList.add(tableId.value().toUpperCase(Locale.US));
                }
            }
            return queryableFieldList;
        });
        for (String filterField : filterFieldList) {
            if (!entityQueryableFields.contains(filterField.toUpperCase(Locale.US))) {
                throw new RuntimeException("invalid field");
            }
        }
    }

    private static void addField(List<String> filterFieldList, List<SortField> sortFieldList) {
        if (sortFieldList != null && !sortFieldList.isEmpty()) {
            for (SortField sortField : sortFieldList) {
                String field = sortField.getField();
                if (field == null || field.isEmpty()) {
                    throw new RuntimeException("invalid field");
                }
                filterFieldList.add(sortField.getField());
            }
        }
    }

    private static void addField(List<String> filterFieldList, CombinedListableFilter combinedListableFilter) {
        if (combinedListableFilter != null && combinedListableFilter.getListableFilters() != null) {
            combinedListableFilter.getListableFilters().forEach(listableFilter -> addField(filterFieldList, listableFilter));
        }
    }

    private static void addField(List<String> filterFieldList, ListableFilter listableFilter) {
        if (listableFilter != null && listableFilter.getBasicFilters() != null) {
            List<BasicFilter> basicFilters = listableFilter.getBasicFilters();
            for (BasicFilter basicFilter : basicFilters) {
                String field = basicFilter.getField();
                if (field == null || field.isEmpty()) {
                    throw new RuntimeException("invalid field");
                }
                filterFieldList.add(field);
            }
        }
    }

    private static void setWrapperWithJoinSymbol(Wrapper wrapper, String joinSymbol, boolean isNew) {
        if ("and".equals(joinSymbol)) {
            if (isNew) {
                wrapper.andNew();
            } else {
                wrapper.and();
            }
        } else if ("or".equals(joinSymbol)) {
            if (isNew) {
                wrapper.orNew();
            } else {
                wrapper.or();
            }
        }
    }

    private static void setWrapperWithListableFilter(Wrapper wrapper, ListableFilter listableFilter) {
        String joinSymbol = listableFilter.getJoinSymbol();
        List<BasicFilter> basicFilters = listableFilter.getBasicFilters();
        for (int i = 0, total = basicFilters.size(); i < total; i++) {
            setWrapperWithBasicFilter(wrapper, basicFilters.get(i));
            if (i != total - 1) {
                setWrapperWithJoinSymbol(wrapper, joinSymbol, false);
            }
        }
    }

    private static void setWrapperWithBasicFilter(Wrapper wrapper, BasicFilter basicFilter) {
        if (!validBasicFilter(basicFilter)) {
            return;
        }
        String field = basicFilter.getField();
        String symbol = basicFilter.getSymbol();
        Object value = basicFilter.getValue();
        if ("=".equals(symbol)) {
            wrapper.eq(field, value);
        } else if ("in".equalsIgnoreCase(symbol)) {
            List<Object> list = new ArrayList<>();
            if (value instanceof List) {
                list = (List)value;
            } else {
                list.add(value);
            }
            if (!list.isEmpty()) {
                wrapper.in(field, list);
            }
        } else if ("like".equalsIgnoreCase(symbol)) {
            String valueStr = String.valueOf(value).trim();
            wrapper.like(field, valueStr);
        }
    }

    private static boolean validBasicFilter(BasicFilter basicFilter) {
        String field = basicFilter.getField();
        Object value = basicFilter.getValue();
        return field != null && !field.isEmpty() && value != null;
    }
}
