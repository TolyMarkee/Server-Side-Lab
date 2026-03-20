package com.wujinkun.helloserver.common;

/**
 * 企业级统一响应体，实验要求的规范返回格式
 * code：状态码（200=成功，500=服务器错误）
 * msg：提示信息
 * data：核心返回数据（成功返回内容，失败为null）
 */
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    // 私有构造，外部仅能通过静态方法调用
    private Result() {}

    // 成功响应：带返回数据
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    // 成功响应：无返回数据（可选）
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        return result;
    }

    // 错误响应：自定义状态码和提示
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    // 通用错误响应：默认500服务器异常
    public static <T> Result<T> error() {
        return error(500, "服务器内部异常");
    }

    // 所有属性的getter/setter（JSON序列化必须）
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}