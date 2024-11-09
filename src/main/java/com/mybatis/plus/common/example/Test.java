package com.mybatis.plus.common.example;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mybatis.plus.common.filter.Filter2Wrapper;
import com.mybatis.plus.common.model.BasicFilter;
import com.mybatis.plus.common.model.CombinedListableFilter;
import com.mybatis.plus.common.model.Filter;
import com.mybatis.plus.common.model.ListableFilter;


import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String inputQueryParamJson = complexQueryWithSort();
        Filter filter = JSON.parseObject(inputQueryParamJson, Filter.class);
        Wrapper<ProjectEntity> wrapper = Filter2Wrapper.filter2Wrapper(ProjectEntity.class, filter);
        System.out.println(wrapper.getSqlSegment());


    }


    public static String easyQuery() { // project_name LIKE '%demo%' and type = 1
        return  "{" +
                "    \"listableFilter\": {" +
                "        \"joinSymbol\": \"and\"," + // 默认为and，可不传
                "        \"basicFilters\": [" +
                "            {" +
                "                \"field\": \"project_name\"," +
                "                \"symbol\": \"like\"," +
                "                \"valueType\": \"String\"," +
                "                \"value\": \"demo\"" +
                "            }," +
                "            {" +
                "                \"field\": \"type\"," +
                "                \"symbol\": \"=\"," + // 默认为=，可不传
                "                \"valueType\": \"int\"," +
                "                \"value\": 1" +
                "            }" +
                "        ]" +
                "    }" +
                "}";
    }

    public static String complexQueryWithSort() { // (project_name LIKE '%demo%' OR create_user LIKE '%funny%') and type = 1 order by create_time desc
        return  "{" +
                "    \"combinedListableFilter\": {" +
                "        \"joinSymbol\": \"and\"," +
                "        \"listableFilters\": [" +
                "            {" +
                "                \"joinSymbol\": \"or\"," +
                "                \"basicFilters\": [" +
                "                    {" +
                "                        \"field\": \"project_name\"," +
                "                        \"symbol\": \"like\"," +
                "                        \"valueType\": \"String\"," +
                "                        \"value\": \"demo\"" +
                "                    }," +
                "                    {" +
                "                        \"field\": \"create_user\"," +
                "                        \"symbol\": \"like\"," +
                "                        \"valueType\": \"String\"," +
                "                        \"value\": \"funny\"" +
                "                    }" +
                "                ]" +
                "            }," +
                "            {" +
                "                \"joinSymbol\": \"and\"," +
                "                \"basicFilters\": [" +
                "                    {" +
                "                        \"field\": \"type\"," +
                "                        \"symbol\": \"=\"," +
                "                        \"valueType\": \"int\"," +
                "                        \"value\": 1" +
                "                    }" +
                "                ]" +
                "            }" +
                "        ]" +
                "    }," +
                "    \"sortFields\": [" +
                "        {" +
                "            \"field\": \"create_time\"," +
                "            \"isAsc\": false" +
                "        }" +
                "    ]" +
                "}";
    }
}
