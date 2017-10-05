package com.nbsaw.miaohu.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageVo extends GenericVo  {
    private String message;
}