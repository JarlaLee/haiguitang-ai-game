package com.jiale.haigui.utils;

import cn.hutool.core.collection.CollUtil;
import com.jiale.haigui.config.AiConfig;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChoice;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 这样做, AiConfig 类仅仅负责加载配置属性，职责单一；AiUtils类 集中所有与Ai相关的逻辑，包括ArkService 的初始化和 Ai部分的对话业务。
 * 和原来的AiConfig 和 AiUtils类相比的话。结构更加好。
 */

@Service
public class AiUtils {

    private final ArkService arkService;

    @Autowired
    public AiUtils(AiConfig aiConfig) {
        // 初始化 ArkService
        ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
        Dispatcher dispatcher = new Dispatcher();
        this.arkService = ArkService.builder()
                .dispatcher(dispatcher)
                .connectionPool(connectionPool)
                .baseUrl(aiConfig.getBaseUrl())
                .apiKey(aiConfig.getApiKey())
                .build();
    }

    /**
     * 聊天（只允许传入系统预设和用户预设）
     *
     * @param systemPrompt 系统预设
     * @param userPrompt   用户预设
     * @return AI 返回的内容
     */
    public String doChat(String systemPrompt, String userPrompt) {
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(systemPrompt).build();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(userPrompt).build();

        messages.add(systemMessage);
        messages.add(userMessage);

        return doChat(messages); // 调用 doChat(List<ChatMessage> chatMessageList) 方法
    }

    /**
     * 更通用的方法，允许用户传入任意条消息列表
     * 调用 ArkService 生成聊天回复
     *
     * @param chatMessageList 消息列表
     * @return AI 返回的内容
     */
    public String doChat(List<ChatMessage> chatMessageList) {
        // 单次调用
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("deepseek-v3-241226")
                .messages(chatMessageList)
                .build();

        List<ChatCompletionChoice> choiceList = arkService.createChatCompletion(chatCompletionRequest)
                .getChoices();

        if (CollUtil.isEmpty(choiceList)) {
            throw new RuntimeException("AI 没有返回任何内容");
        }

        String content = (String) choiceList.get(0).getMessage().getContent();
        System.out.println("AI 返回内容：" + content);
        return content;
    }
}
