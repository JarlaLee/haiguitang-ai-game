<template>
  <a-row type="flex" justify="center" align="middle" class="home-container">
    <a-col :span="24" class="text-center">
      <div class="center-content">
        <h1 class="title">AI 海龟汤</h1>
        <a-button type="primary" size="large" class="start-button" @click="startNewGame">开始游戏</a-button>
      </div>
    </a-col>
  </a-row>
</template>

<script setup>
    import { useRouter } from 'vue-router'
    import { nanoid } from 'nanoid'

    const router = useRouter()

    const startNewGame = () => {
      // 使用 nano id 创建随机的 roomID
      // const roomId = nanoid();


      // 生成唯一房间号, 根据时间戳生成房间号
      const roomId = Date.now()
      router.push({ name: 'chat', params: { roomId } })
}
</script>

<style scoped>
.home-container {
  height: 100vh;
  background: linear-gradient(135deg, #f5f7fa, #c3cfe2); /* 纯黑色背景 */
}

.center-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.title {
  font-size: 2.5rem;
  margin-bottom: 2rem;
  color: #2c3e50; /* 白色文字 */
}

.start-button {
  background: linear-gradient(135deg, #6a11cb, #2575fc); /* 蓝色按钮 */
  color: #fff;
  border: none;
  transition: background 0.3s ease;
}

.start-button:hover {
  background: linear-gradient(135deg, #2575fc, #6a11cb); /* 悬停时按钮颜色变浅 */
}
</style>
