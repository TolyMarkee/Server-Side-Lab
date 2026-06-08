package com.wujinkun.helloserver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天响应VO（实验10）
 * 返回用户问题和大模型回答
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseVO {
    private String question;
    private String answer;
}
