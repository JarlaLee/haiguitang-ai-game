package com.jiale.haigui.controller;


import com.jiale.haigui.model.ChatRoom;
import com.jiale.haigui.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * AI 对话接口
 */
@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    /**
     * 与 AI 对话
     *
     * @param roomId  聊天室 ID
     * @param message 用户输入的消息
     * @return AI 的回复
     */
    @PostMapping("/{roomId}/send")
    public String doChat(@PathVariable long roomId, @RequestParam String message) {
        log.info("RoomId:{};Message:{}", roomId, message);
        return chatService.doChat(roomId, message);
    }


    /**
     * 获取所有聊天室列表
     *
     * @return 聊天室列表
     */
    @GetMapping("/rooms")
    public List<ChatRoom> getChatRoomList() {
        return chatService.getChatRoomList();
    }
}