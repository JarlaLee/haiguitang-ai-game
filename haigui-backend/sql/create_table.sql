CREATE DATABASE IF NOT EXISTS chat_db;

USE chat_db;


CREATE TABLE chat_messages (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               room_id BIGINT NOT NULL,
                               role VARCHAR(20) NOT NULL, -- 消息角色（user, assistant, system）
                               content TEXT NOT NULL,     -- 消息内容
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 消息创建时间
);


-- 添加索引, 未来可以考虑
# CREATE INDEX IF NOT EXISTS idx_room_id ON chat_messages (room_id);
# CREATE INDEX IF NOT EXISTS idx_created_at ON chat_messages (created_at);