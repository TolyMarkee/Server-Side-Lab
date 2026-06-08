package com.wujinkun.helloserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 聊天记录实体（实验11）
 * 用于保存每一轮会话记录，当前作为缓存对象使用
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRecord {
    /** 会话编号 */
    private String sessionId;
    /** 用户问题 */
    private String userMessage;
    /** 大模型回答 */
    private String assistantMessage;
    /** 记录时间 */
    private LocalDateTime createTime;
}
