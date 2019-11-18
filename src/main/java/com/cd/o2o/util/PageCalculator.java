package com.cd.o2o.util;

public class PageCalculator {

    /**
     *
     * @param pageIndex 页码，注意是页码不是行号
     * @param pageSize 一页有多少条数据
     * @return
     */
    public static int calculatorRowIndex(int pageIndex,int pageSize){
        return (pageIndex > 0) ? (pageIndex - 1)*pageSize : 0;
    }

}
