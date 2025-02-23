package com.usareboot.back.services.vk;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

@Service
public class StateService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public String getUserState(int userId) {
        return redisTemplate.opsForValue().get("user_state:" + userId);
    }

    public void setUserState(int userId, String state) {
        redisTemplate.opsForValue().set("user_state:" + userId, state);
    }
}