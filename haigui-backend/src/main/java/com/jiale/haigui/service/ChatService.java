package com.jiale.haigui.service;

import com.jiale.haigui.model.ChatRoom;
import com.jiale.haigui.model.Message;

import java.util.List;

/**
 * 聊天服务
 */
public interface ChatService {

    /**
     * 和 AI 对话
     *
     * @param roomId 聊天室 id
     * @param message 用户自己输入的消息
     * @return AI 的结果
     */
    String doChat(long roomId, String message);

    /**
     * 获取对话列表
     *
     * @return 聊天室列表
     */
    List<ChatRoom> getChatRoomList();


    void addMessageToDatabase(Message message);
}
