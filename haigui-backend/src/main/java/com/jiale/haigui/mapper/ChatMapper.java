package com.jiale.haigui.mapper;

import com.jiale.haigui.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {

    @Insert("INSERT INTO chat_messages (room_id, role, content, created_at) " +
            "VALUES (#{roomId}, #{role}, #{content}, #{createdAt})")
    void insert(Message message);
}
