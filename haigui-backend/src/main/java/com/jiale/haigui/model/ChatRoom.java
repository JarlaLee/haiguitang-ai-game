package com.jiale.haigui.model;

import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import lombok.Data;

import java.util.List;

// model 包下，存放项目中自定义的 类。 这些类用来模拟现实。


/**
 * 聊天房间
 */
@Data
public class ChatRoom {

    private Long roomId;
    private List<ChatMessage> chatMessageList;
}
