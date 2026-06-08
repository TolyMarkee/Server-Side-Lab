package com.wujinkun.helloserver.controller;

import com.wujinkun.helloserver.common.Result;
import com.wujinkun.helloserver.dto.ChatRequestDTO;
import com.wujinkun.helloserver.service.ChatService;
import com.wujinkun.helloserver.vo.ChatResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 聊天控制器（实验11改造）
 * 提供多轮会话聊天接口，支持历史上下文记忆
 */
@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public Result<ChatResponseVO> chat(@RequestBody ChatRequestDTO request) {
        ChatResponseVO vo = chatService.chat(request);
        return Result.success(vo);
    }
}
