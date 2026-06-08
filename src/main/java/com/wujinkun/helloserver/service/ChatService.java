package com.wujinkun.helloserver.service;

import com.wujinkun.helloserver.dto.ChatRequestDTO;
import com.wujinkun.helloserver.vo.ChatResponseVO;

/**
 * 聊天服务接口（实验11改造）
 * 封装大模型聊天调用逻辑，支持会话记忆
 */
public interface ChatService {
    /**
     * 发送消息并获取大模型回复（支持多轮会话上下文）
     * @param requestDTO 包含 sessionId 和 message
     * @return 包含用户问题和模型回答的响应对象
     */
    ChatResponseVO chat(ChatRequestDTO requestDTO);
}
