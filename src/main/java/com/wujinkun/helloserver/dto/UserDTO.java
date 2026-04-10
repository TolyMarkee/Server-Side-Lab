package com.wujinkun.helloserver.dto;

/**
 * 用户数据传输对象
 * 用于接收前端注册/登录传来的 username 和 password
 */
public class UserDTO {
    private String username;
    private String password;

    // Getter & Setter（必须有，否则Controller接收不到参数）
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}