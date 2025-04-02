package com.jiale.haigui.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AiUtilsTest {
    // 必须添加SpringBootTest 才可以使用Autowired

    @Autowired
    private AiUtils aiUtils;


    @Test
    void doChat(){

        String answer = aiUtils.doChat("你是一位程序员大佬", "帮我写一个java程序");
        System.out.println(answer);


        // AI不能记录问过的问题
        String res = aiUtils.doChat("你是一位程序员大佬", "你还记得我问的问题吗?");
        System.out.println(res);
    }
}
