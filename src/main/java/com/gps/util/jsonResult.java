package com.gps.util;

import lombok.Data;

@Data
public class jsonResult<T> {
    private Integer code;
    private String msg;
    private T data;
}
