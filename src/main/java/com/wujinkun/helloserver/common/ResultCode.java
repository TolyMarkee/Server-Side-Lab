package com.wujinkun.helloserver.common;

public enum ResultCode {
    // 基础状态码（原有，完全保留）
    SUCCESS(200, "操作成功"),
    ERROR(500, "系统繁忙，请稍后再试"),
    TOKEN_INVALID(401, "登录凭证已缺失或过期，请重新登录"),
    PARAM_ERROR(400, "请求参数错误"),
    NOT_FOUND(404, "请求资源不存在"),

    // 任务4 新增用户相关状态码
    USER_HAS_EXISTED(4001, "该用户名已被注册"),
    USER_NOT_EXIST(4002, "该用户不存在"),
    PASSWORD_ERROR(4003, "账号或密码错误"); // 【核心修正】最后一个常量必须用「英文分号;」结尾！

    // 私有成员变量（枚举属性，原样保留）
    private final Integer code;
    private final String msg;

    // 构造方法（枚举默认私有，无需写private，原样保留）
    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 【必须保留】getter方法（供Result类读取状态码/消息，漏写会报错）
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}