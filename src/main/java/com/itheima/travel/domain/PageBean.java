package com.itheima.travel.domain;

import lombok.Data;

import java.util.List;

@Data
public class PageBean<E> {

    //起始页
    private Integer begin;
    //结束页
    private Integer end;
    private Integer totalCount; //总记录数
    private Integer totalPage;// 总页数
    private List<E> list;// 结果集
    private Integer pageNum;// 当前页
    private Integer pageSize;// 每页个数

    public Integer getBegin() {
        Calculation();
        return begin;
    }

    public Integer getEnd() {
        return end;
    }

    private void Calculation(){
        if (totalPage < 10){
            begin = 1;
            end = totalPage;
        }else {
            begin = pageNum - 5;
            end = pageNum + 4;
            if (begin < 1){
                begin = 1;
                end = begin + 9;
            }else if (end > totalPage){
                begin = totalPage - 9;
                end = totalPage;
            }
        }
    }

}
