package com.wujinkun.helloserver.common;
public enum ResultCode {
    // 基础成功/失败状态码
    SUCCESS(200, "操作成功"),
    ERROR(500, "系统繁忙，请稍后再试"),
    // 权限相关状态码（拦截器用）
    TOKEN_INVALID(401, "登录凭证已缺失或过期，请重新登录"),
    // 可选扩展：参数错误、资源不存在等
    PARAM_ERROR(400, "请求参数错误"),
    NOT_FOUND(404, "请求资源不存在");

    private final Integer code;
    private final String msg;

    // 构造方法
    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // Getter方法（必须有，用于序列化）
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}