package com.sun.quandemo.model;

import lombok.Data;

/**
 * 通用响应数据模型
 * @param <T> 响应数据类型
 */
@Data
public class ResponseData<T> {
    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应时间戳
     */
    private Long timestamp;

    /**
     * 创建成功响应
     */
    public static <T> ResponseData<T> success(T data) {
        ResponseData<T> response = new ResponseData<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(data);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }

    /**
     * 创建错误响应
     */
    public static <T> ResponseData<T> error(String message) {
        ResponseData<T> response = new ResponseData<>();
        response.setCode(500);
        response.setMessage(message);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
} 