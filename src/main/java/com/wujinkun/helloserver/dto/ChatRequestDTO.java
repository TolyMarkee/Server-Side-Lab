package com.wujinkun.helloserver.dto;

import lombok.Data;

/**
 * 聊天请求DTO（实验11扩展）
 * 接收用户输入的会话编号和问题文本
 */
@Data
public class ChatRequestDTO {
    /** 会话编号，用于标识同一轮连续对话 */
    private String sessionId;
    /** 当前用户输入 */
    private String message;
}
