<template>
  <a-layout class="chat-container">
    <!-- 聊天区域 -->
    <a-layout-content class="chat-content">
      <div class="room-id">房间号: {{ roomId }}</div>
      <div class="message-list" ref="messageListRef">
        <div v-for="(msg, index) in messages" :key="index" class="message-item">
          <!-- AI 消息 -->
          <a-comment v-if="msg.isAI" class="ai-message">
            <template #avatar>
              <a-avatar src="ai-avatar.png" />
            </template>
            <template #content>
              <div class="message-bubble ai">{{ msg.content }}</div>
            </template>
          </a-comment>

          <!-- 用户消息 -->
          <a-comment v-else class="user-message">
            <template #avatar>
              <a-avatar src="user-avatar.png" />
            </template>
            <template #content>
              <div class="message-bubble user">{{ msg.content }}</div>
            </template>
          </a-comment>
        </div>
      </div>
    </a-layout-content>

    <!-- 输入区域 -->
    <a-layout-footer class="input-area">
      <div class="action-buttons">
        <a-button type="primary" :disabled="isGameStarted" class="start-button" @click="handleStart"> 开始 </a-button>
        <a-button danger :disabled="!isGameStarted || isGameEnded" class="end-button" @click="handleEnd">结束</a-button>
      </div>
      <a-input-group compact>
        <a-input
          v-model:value="inputMessage"
          placeholder="输入你的问题..."
          :disabled="!isGameStarted || isGameEnded"
          class="input-box"
          @pressEnter="handleSend"
        />
        <a-button type="primary" :disabled="!isGameStarted || isGameEnded" class="send-button" @click="handleSend">发送</a-button>
      </a-input-group>
    </a-layout-footer>
  </a-layout>
</template>

<script setup>
import { ref, nextTick } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const roomId = ref(route.params.roomId); // 从路由中读取房间号

const messages = ref([]); // 消息列表
const inputMessage = ref(''); // 用户输入的消息
const messageListRef = ref(null); // 消息列表的 DOM 引用
const isGameStarted = ref(false); // 游戏是否开始
const isGameEnded = ref(false); // 游戏是否结束

// 自动滚动到底部
const scrollToBottom = () => {
  nextTick(() => {
    if (messageListRef.value) {
      messageListRef.value.scrollTop = messageListRef.value.scrollHeight;
    }
  });
};

// 发送消息到后端
const sendMessage = async (message) => {
  if (!message.trim()) return; // 如果消息为空，直接返回

  try {
    // 用户消息
    messages.value.push({
      content: message,
      isAI: false
    });

    // AI 回复
    const response = await axios.post(
      `http://localhost:8080/api/chat/${roomId.value}/send`,
      null,
      { params: { message } }
    );

    messages.value.push({
      content: response.data,
      isAI: true
    });

    // 检查游戏结束
    if (response.data.includes('游戏已结束')) {
      isGameEnded.value = true;
    }

    scrollToBottom();
  } catch (error) {
    console.error('发送消息失败:', error);
  }
};

// 处理开始游戏
const handleStart = async () => {
  isGameStarted.value = true; // 标记游戏已开始
  await sendMessage('开始'); // 发送“开始游戏”消息
};

// 处理结束游戏
const handleEnd = async () => {
  isGameEnded.value = true; // 标记游戏已结束
  await sendMessage('结束游戏'); // 发送“结束游戏”消息
};

// 处理发送消息
const handleSend = async () => {
  if (!inputMessage.value.trim()) return; // 如果消息为空，直接返回
  await sendMessage(inputMessage.value); // 发送用户输入的消息
  inputMessage.value = ''; // 清空输入框
};
</script>

<style scoped>
.chat-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: linear-gradient(135deg, #e0f7fa, #f3e5f5); /* 渐变色背景 */
  font-family: 'Poppins', sans-serif; /* 使用现代字体 */
}

.chat-content {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

.room-id {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-bottom: 16px;
}

.message-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.message-item {
  display: flex;
  align-items: flex-start;
}

.ai-message {
  margin-right: auto;
}

.user-message {
  margin-left: auto;
}

.message-bubble {
  padding: 12px 16px;
  border-radius: 12px;
  max-width: 70%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

.ai {
  background-color: #f0f0f0;
  color: #333;
}

.user {
  background-color: #1890ff;
  color: white;
}

.input-area {
  padding: 16px;
  background-color: #fff;
  border-top: 1px solid #e8e8e8;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

.action-buttons {
  margin-bottom: 12px;
  display: flex;
  gap: 8px;
}

.start-button {
  background-color: #1890ff;
  border: none;
  box-shadow: 0 2px 4px rgba(24, 144, 255, 0.3); /* 添加阴影效果 */
}

.end-button {
  background-color: #ff4d4f;
  border: none;
  box-shadow: 0 2px 4px rgba(255, 77, 79, 0.3); /* 添加阴影效果 */
}

.input-box {
  border-radius: 8px;
  border: 1px solid #1890ff;
  padding: 8px 12px;
  flex: 1;
}

.send-button {
  background-color: #1890ff;
  border: none;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(24, 144, 255, 0.3); /* 添加阴影效果 */
}
</style>
