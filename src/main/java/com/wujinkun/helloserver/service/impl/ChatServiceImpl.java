package com.wujinkun.helloserver.service.impl;

import com.wujinkun.helloserver.dto.ChatRequestDTO;
import com.wujinkun.helloserver.service.ChatService;
import com.wujinkun.helloserver.vo.ChatResponseVO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 聊天服务实现（实验11改造）
 * 通过 Spring AI ChatClient 调用 DashScope Qwen 模型
 * 集成 Redis 实现多轮会话记忆与历史上下文管理
 */
@Service
public class ChatServiceImpl implements ChatService {

    private static final String REDIS_KEY_PREFIX = "chat:session:";
    /** 保留最近 3 轮对话（每轮 2 条：用户 + 助手） */
    private static final int MAX_HISTORY_ENTRIES = 6;

    private final ChatClient chatClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 构造器注入 ChatModel，构建 ChatClient 实例
     * 设置默认系统提示词
     */
    @Autowired
    public ChatServiceImpl(ChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel)
                .defaultSystem("你是一名专业、友好、简洁的中文智能助手")
                .build();
    }

    @Override
    public ChatResponseVO chat(ChatRequestDTO requestDTO) {
        String sessionId = requestDTO.getSessionId();
        String message = requestDTO.getMessage();

        // sessionId 为空时，退化为单轮聊天（无历史上下文）
        if (!StringUtils.hasText(sessionId)) {
            String answer = chatClient.prompt(message).call().content();
            return new ChatResponseVO(message, answer);
        }

        // 1. 构造 Redis Key
        String redisKey = REDIS_KEY_PREFIX + sessionId;

        // 2. 读取历史聊天记录（最近 3 轮）
        List<String> history = stringRedisTemplate.opsForList().range(redisKey, 0, -1);

        // 3. 构建最终提示词（包含历史上下文）
        String finalPrompt = buildPromptWithHistory(history, message);

        // 4. 调用大模型
        String answer = chatClient.prompt(finalPrompt).call().content();

        // 5. 保存本轮记录到 Redis
        String record = "用户：" + message + "\n助手：" + answer;
        stringRedisTemplate.opsForList().rightPush(redisKey, record);

        // 6. 只保留最近 3 轮（6 条记录）
        Long size = stringRedisTemplate.opsForList().size(redisKey);
        if (size != null && size > MAX_HISTORY_ENTRIES) {
            stringRedisTemplate.opsForList().trim(redisKey, size - MAX_HISTORY_ENTRIES, -1);
        }

        // 7. 返回结果
        return new ChatResponseVO(message, answer);
    }

    /**
     * 构建包含历史上下文的提示词
     */
    private String buildPromptWithHistory(List<String> history, String currentMessage) {
        if (history == null || history.isEmpty()) {
            return currentMessage;
        }

        // 拼接历史对话
        StringBuilder historyText = new StringBuilder();
        historyText.append("以下是历史对话：\n");
        for (String record : history) {
            historyText.append(record).append("\n");
        }

        // 附加当前问题
        historyText.append("\n当前用户问题：").append(currentMessage);

        return historyText.toString();
    }
}
