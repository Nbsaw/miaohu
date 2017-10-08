package com.nbsaw.miaohu.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import java.io.Serializable;

@Data
public class PageDTO<T> implements Serializable {
    private int nowPage;
    private int totalPage;
    private Long count;
    private T data;

    private PageDTO setNowPage(int nowPage){
        this.nowPage = nowPage;
        return this;
    }

    private PageDTO setTotalPage(int totalPage){
        this.totalPage = totalPage;
        return this;
    }

    private PageDTO setCount(Long count){
        this.count = count;
        return this;
    }

    private PageDTO setData(T data){
        this.data = data;
        return this;
    }

    public PageDTO(Page page, T data){
        this.setCount(page.getTotalElements())
                .setTotalPage(page.getTotalPages())
                .setNowPage(page.getNumber())
                .setData(data);
    }
}
