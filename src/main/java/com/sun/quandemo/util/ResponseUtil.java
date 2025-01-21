package com.sun.quandemo.util;

import com.sun.quandemo.model.BaseResponse;

public class ResponseUtil {
    
    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(data);
        return response;
    }
    
    public static <T> BaseResponse<T> error(String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(500);
        response.setMessage(message);
        return response;
    }
} 