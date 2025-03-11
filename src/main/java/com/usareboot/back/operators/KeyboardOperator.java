package com.usareboot.back.operators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vk.api.sdk.objects.messages.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeyboardOperator {
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

    public Keyboard getKeyboardForRates(List<String> rates) {
        Keyboard keyboard = new Keyboard();
        List<List<KeyboardButton>> keyboardButtons = new ArrayList<>();

        for (String rate : rates) {
            KeyboardButton button = new KeyboardButton();
            KeyboardButtonAction action = new KeyboardButtonAction();
            action.setLabel(rate);
            action.setType(TemplateActionTypeNames.TEXT);
            button.setAction(action);
            button.setColor(KeyboardButtonColor.DEFAULT); // Можно выбрать другой цвет, если нужно

            List<KeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboardButtons.add(row);
        }

        keyboard.setButtons(keyboardButtons);
//        keyboard.setOneTime(true); // Клавиатура исчезнет после нажатия на кнопку
        keyboard.setInline(true); // Клавиатура будет отображаться внизу экрана
        return keyboard;
    }
}
