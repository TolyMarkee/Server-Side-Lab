package com.wujinkun.helloserver.common;

/**
 * 统一返回前端的泛型响应体（实验要求：统一响应结构）
 * @param <T> 响应数据泛型，支持任意数据类型
 */
public class Result<T> {
    // 响应信息
    private String msg;
    // 业务状态码（对应ResultCode枚举）
    private Integer code;
    // 响应数据（泛型）
    private T data;

    // 静态工厂方法：成功回调（带数据）
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    // 重载：成功回调（无数据，仅返回成功状态）
    public static <T> Result<T> success() {
        return success(null);
    }

    // 静态工厂方法：失败回调（传入自定义状态码）
    public static <T> Result<T> error(ResultCode resultCode) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMsg(resultCode.getMsg());
        result.setData(null);
        return result;
    }

    // 重载：失败回调（自定义错误信息，用于全局异常处理）
    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    // Getter & Setter（IDEA可自动生成，此处写全避免报错）
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}