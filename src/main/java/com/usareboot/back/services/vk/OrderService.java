package com.usareboot.back.services.vk;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class OrderService {

   /* private final KeyboardService keyboardService;
    private final StateService stateService;

    public OrderService(KeyboardService keyboardService, StateService stateService) {
        this.keyboardService = keyboardService;
        this.stateService = stateService;
    }

    public String handleOrderStep(int userId, String text) {
        String userState = stateService.getUserState(userId);

        switch (userState) {
            case "make_order":
                stateService.setUserState(userId, "enter_product_name");
                return "Введите название товара:";
            case "enter_product_name":
                stateService.setUserState(userId, "enter_product_link");
                return "Введите ссылку на товар:";
            case "enter_product_link":
                stateService.setUserState(userId, "upload_photo");
                return "Загрузите фото товара:";
            case "upload_photo":
                stateService.setUserState(userId, "enter_size");
                return "Введите размер товара:";
            case "enter_size":
                stateService.setUserState(userId, "enter_quantity");
                return "Введите количество товара:";
            case "enter_quantity":
                stateService.setUserState(userId, "choose_album");
                Map<String, String> albums = Map.of("Альбом 1", "1", "Альбом 2", "2");
                return keyboardService.getAlbumKeyboard(userId, albums);
            case "choose_album":
                stateService.setUserState(userId, "start");
                return "Ваш заказ оформлен!";
            default:
                return "Неизвестная команда.";
        }
    }*/
}