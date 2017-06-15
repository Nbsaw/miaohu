package com.nbsaw.miaohu.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nbsaw on 2017/6/15.
 */
@Data
public class QuestionsVo<T> implements Serializable {
    private int code;
    private List<T> result;
}
