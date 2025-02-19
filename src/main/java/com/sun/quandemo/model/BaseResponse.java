package com.sun.quandemo.model;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private Integer code;
    private String message;
    private T data;
} 