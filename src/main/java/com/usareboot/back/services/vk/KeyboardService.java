package com.usareboot.back.services.vk;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.objects.messages.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import com.vk.api.sdk.objects.messages.Keyboard;
@Service
@Slf4j
public class KeyboardService {

    public Keyboard getStartKeyboard() throws JsonProcessingException {
        Keyboard keyboard = new Keyboard();
        keyboard.setOneTime(true); // Клавиатура исчезнет после нажатия

        /*KeyboardButtonActionText buttonAction = new KeyboardButtonActionText();
        buttonAction.setLabel("Сделать заказ");
        buttonAction.setType(KeyboardButtonActionTextType.TEXT);

        ObjectMapper mapper = new ObjectMapper();
        var jsonButton = mapper.writeValueAsString(buttonAction);
        JsonObject jsonObject = JsonParser.parseString(jsonButton).getAsJsonObject();

        // Создаем кнопку
        KeyboardButton button = new KeyboardButton()
                .setAction(new KeyboardButtonPropertyAction(jsonObject)
                ) // Тип кнопки (текстовая)
                .setColor(KeyboardButtonColor.PRIMARY); // Цвет кнопки*/

        KeyboardButtonAction buttonAction = new KeyboardButtonAction();
        buttonAction.setLabel("Сделать заказ");
        buttonAction.setType(TemplateActionTypeNames.TEXT);

        KeyboardButton button = new KeyboardButton();
        button.setAction(buttonAction);
        button.setColor(KeyboardButtonColor.PRIMARY); // Цвет кнопки

        List<List<KeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Collections.singletonList(button));

        keyboard.setButtons(buttons);
        return keyboard;
    }

    public String getAlbumKeyboard(int userId, Map<String, String> albums) {
        Map<String, Object> keyboard = new HashMap<>();
        keyboard.put("one_time", true);
        Map<String, Object>[] buttons = new Map[albums.size()];
        int i = 0;
        for (String album : albums.keySet()) {
            buttons[i] = Map.of("action", Map.of("type", "text", "label", album), "color", "secondary");
            i++;
        }
        keyboard.put("buttons", buttons);

        return "Выберите альбом:";
    }
}