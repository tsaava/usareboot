package com.usareboot.back.services.vk;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.models.vk.VkMessageNew;
import com.usareboot.back.models.vk.VkPhotoObject;
import com.usareboot.back.models.vk.VkTypeObject;
import com.usareboot.back.operators.BotVkOperator;
import com.usareboot.back.operators.KeyboardOperator;
import com.vk.api.sdk.objects.messages.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.vk.api.sdk.objects.messages.Keyboard;
@Service
@Slf4j
@RequiredArgsConstructor
public class KeyboardService {
    private final BotVkOperator botVkOperator;
    private final KeyboardOperator keyboardOperator;

 /*   public void sendKeyboard(Integer userId) throws JsonProcessingException {
            Keyboard rateKeyboard = getKeyboardForRates();

    }

    public Keyboard getStartKeyboard() throws JsonProcessingException {
        return keyboardOperator.getStartKeyboard();
    }

    public Keyboard getKeyboardForRates() throws JsonProcessingException {
        return keyboardOperator.getKeyboardForRates();
    }*/
}