package com.usareboot.back.services.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.entities.AlbumMappingDictionaryEntity;
import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.entities.AlbumsItemsEntity;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.vk.VkBotResponseDTO;
import com.usareboot.back.models.vk.VkEvent;
import com.usareboot.back.operators.AlbumItemOperator;
import com.usareboot.back.operators.BotVkOperator;
import com.usareboot.back.operators.KeyboardOperator;
import com.usareboot.back.services.AlbumsItemsService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import com.vk.api.sdk.objects.messages.Message;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkBotService {
    private final Map<Integer, OrderState> userStates = new HashMap<>();
    private final Map<Integer, AlbumsItemsEntity> userOrders = new HashMap<>();
    private final VkApiClient vk;
    private final GroupActor actor;
    private final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> ThreadLocalRandom.current().nextInt(10000, 100000));
    private final AlbumsItemsService albumsItemsService;
    private final BotVkOperator botVkOperator;
    private final AlbumItemOperator albumItemOperator;
    private final KeyboardOperator keyboardOperator;
    //    private final ItemOperator itemOperator;
    private static final Map<String, Integer> ALBUMS = Map.of(
            "Одежда", 1,
            "Обувь", 2,
            "Аксессуары", 3
    );

    public void handleMessage(Message message) throws JsonProcessingException {
        int userId = Math.toIntExact(message.getFromId());
        String text = message.getText();

        if (text.equals("Сделать заказ")) {
            startOrder(userId);
            return;
        }

        OrderState state = userStates.getOrDefault(userId, OrderState.NONE);
        AlbumsItemsEntity order = new AlbumsItemsEntity();

        switch (state) {
            case WAITING_NAME:
                order.setAlbumItemName(text);
                askForLink(userId);
                break;
            case WAITING_LINK:
                order.setItemUrl(text);
                askForPhoto(userId);
                break;
            case WAITING_PHOTO:
                if (!message.getAttachments().isEmpty()) {
                    order.setVkPhotoPath(String.valueOf(message.getAttachments().get(0).getPhoto().getSizes().get(0).getUrl()));
                    askForSize(userId);
                }
                break;
            case WAITING_SIZE:
                order.setItemSize(text);
                askForQuantity(userId);
                break;
            case WAITING_QUANTITY:
                order.setAlbumItemCount(Integer.parseInt(text));
                showAlbumSelection(userId);
                break;
            case SELECTING_ALBUM:
                finishOrder(userId, text);
                break;
        }
    }

    private void startOrder(int userId) {
        userStates.put(userId, OrderState.WAITING_NAME);
        userOrders.put(userId, new AlbumsItemsEntity());
        sendMessage(userId, "Введите название товара:");
    }

    private void askForLink(int userId) {
        userStates.put(userId, OrderState.WAITING_LINK);
        sendMessage(userId, "Введите ссылку на товар:");
    }

    private void askForPhoto(int userId) {
        userStates.put(userId, OrderState.WAITING_PHOTO);
        sendMessage(userId, "Загрузите фото товара:");
    }

    private void askForSize(int userId) {
        userStates.put(userId, OrderState.WAITING_SIZE);
        sendMessage(userId, "Введите размер:");
    }

    private void askForQuantity(int userId) {
        userStates.put(userId, OrderState.WAITING_QUANTITY);
        sendMessage(userId, "Введите количество:");
    }

    private void showAlbumSelection(int userId) throws JsonProcessingException {
        userStates.put(userId, OrderState.SELECTING_ALBUM);

        Keyboard keyboard = new Keyboard();
        List<List<KeyboardButton>> buttons = new ArrayList<>();
        List<KeyboardButton> row = new ArrayList<>();
        /*for (String albumName : ALBUMS.keySet()) {
            // Создаем действие для кнопки
            KeyboardButtonActionText action = new KeyboardButtonActionText()
                    .setLabel(albumName) // Текст на кнопке
                    .setType(KeyboardButtonActionTextType.TEXT)
                    .setPayload("{\"button\": \"1\"}"); // Тип кнопки (текстовая)

            ObjectMapper mapper = new ObjectMapper();
            var jsonButton = mapper.writeValueAsString(action);
            JsonObject jsonObject = JsonParser.parseString(jsonButton).getAsJsonObject();

            // Создаем кнопку
            KeyboardButton button = new KeyboardButton()
                    .setAction(new KeyboardButtonPropertyAction(jsonObject)
                            ) // Тип кнопки (текстовая)
                    .setColor(KeyboardButtonColor.PRIMARY); // Цвет кнопки

            // Добавляем кнопку в строку
            List<KeyboardButton> rows = new ArrayList<>();
            rows.add(button);

            // Добавляем строку в список кнопок
            buttons.add(row);
           *//* row.add(new KeyboardButton()
                    .setAction(new KeyboardButtonAction()
                            .setLabel(albumName)
                            .setType(KeyboardButtonActionType.TEXT))
                    .setColor(KeyboardButtonColor.PRIMARY));

            if (row.size() == 2) {
                buttons.add(row);
                row = new ArrayList<>();
            }*//*
        }*/

        if (!row.isEmpty()) {
            buttons.add(row);
        }

        keyboard.setButtons(buttons);
        keyboard.setInline(true);

        try {
            vk.messages().send(actor)
                    .message("Выберите альбом:")
                    .userId(userId)
                    .keyboard(keyboard)
                    .randomId(random.nextInt())
                    .execute();
        } catch (ApiException | ClientException e) {
            log.error(e.toString());
        }
    }

    private void finishOrder(int userId, String albumName) {
        AlbumsItemsEntity order = userOrders.get(userId);
//        order.setAlbumId(ALBUMS.get(albumName));

        // Save order to database or process it

        userStates.remove(userId);
        userOrders.remove(userId);

        sendMessage(userId, "Заказ успешно создан!");
    }

    private void sendMessage(int userId, String message) {
        try {
            vk.messages().send(actor)
                    .message(message)
                    .userId(userId)
                    .randomId(random.nextInt())
                    .execute();
        } catch (ApiException | ClientException e) {
            log.error(e.toString());
        }
    }

    private Random random = new Random();

    private enum OrderState {
        NONE,
        WAITING_NAME,
        WAITING_LINK,
        WAITING_PHOTO,
        WAITING_SIZE,
        WAITING_QUANTITY,
        SELECTING_ALBUM
    }


    @Async
    public void saveClientItem(String itemName, String itemUrl, String itemPhotoPath, String itemSize, String itemCount, String clientId, String cost, String itemColor, Integer timestamp, String vk_event) throws IOException {
        try {
            var eventId = threadLocal.get();
            VkBotResponseDTO data;
            VkEvent vkPhotoObject;
            log.info("[Сценарий saveClientItem][Шаг: Начало][EventID: {}]", eventId);

            log.info("[Сценарий saveClientItem][Шаг: конвертирование данных][EventID: {}]", eventId);
            ObjectMapper mapper = new ObjectMapper();

            log.info("[Сценарий saveClientItem][Шаг: проверка наличия значения clientId][EventID: {}]", eventId);
            if (!clientId.isEmpty()) {
                Double cost1 = Double.valueOf(Optional.of(cost.trim()).orElse("0"));
                data = VkBotResponseDTO.builder()
                        .itemName(itemName)
                        .itemUrl(itemUrl)
                        .itemPhotoPath(itemPhotoPath)
                        .itemSize(itemSize)
                        .itemCount(Integer.valueOf(Optional.of(itemCount.trim()).orElse("1")))
                        .clientId(Integer.valueOf(clientId.trim()))
                        .cost(cost1)
                        .itemColor(itemColor)
                        .timestamp(timestamp)
                        .cost(cost.isEmpty() ? Double.parseDouble(cost) : 0)
                        .build();
            } else {
                log.error("clientId был равен 0");
                throw new RuntimeException("Ошибка при сохранении заказа клиента: id клиента не был передан");
            }
            if (vk_event != null) {
                JsonObject json = JsonParser.parseString(vk_event).getAsJsonObject();
                vkPhotoObject = mapper.readValue(json.toString(), VkEvent.class);
                data.setVk_event(vkPhotoObject);
            }
            log.info("[Сценарий saveClientItem][Шаг: вывод полученных данных: {}][EventID: {}]", data, eventId);

            if (itemUrl == null || vk_event == null)
                throw new RuntimeException("Ссылка на товар или фотография не была введена! Ошибка сохранения заказа");

            log.info("[Сценарий saveClientItem][Шаг: преобразование данных в AlbumItem][EventID: {}]", eventId);
            AlbumsItemsDTO albumItem = botVkOperator.getAlbumItem(data);

            log.info("[Сценарий saveClientItem][Шаг: сохранение данных товара в таблицу AlbumItem][EventID: {}]", eventId);
            AlbumsItemsEntity albumsItemsEntity = albumItemOperator.saveAlbumItem(albumItem);
            albumItem.setAlbumItemId(albumsItemsEntity.getAlbumItemId());
            log.info("[Сценарий saveClientItem][Шаг: сохранение данных товара в таблицу item][EventID: {}]", eventId);
            botVkOperator.saveItem(albumItem, data);

        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    @Async
    public void getRates(String itemUrl, Integer userId) {
        try {
            var eventId = threadLocal.get();
            var text = "Выберите подходящий курс";
            log.info("[Сценарий getRates][Шаг: Начало][EventID: {}]", eventId);

            log.info("[Сценарий getRates][Шаг: Получаем хост][EventID: {}]", eventId);
            String host = botVkOperator.getHost(itemUrl);

            log.info("[Сценарий getRates][Шаг: Поиск открытого альбома по ссылке: {}][ClientId: {}][EventID: {}]", itemUrl, userId, eventId);
            AlbumsEntity albumsEntity = botVkOperator.getAlbumsEntity(host);
            List<String> courseAlbum = new ArrayList<>();
//        if (albumsEntity != null)
            courseAlbum = Arrays.stream(albumsEntity.getCourseAlbum().split("/")).toList();
//        else {
//            courseAlbum.add("Курс альбома не сопоставлен");
//        }

            log.info("[Сценарий getRates][Шаг: Формирование клавиатуры с курсами][EventID: {}]", eventId);
            Keyboard rateKeyboard = keyboardOperator.getKeyboardForRates(courseAlbum);

            log.info("[Сценарий getRates][Шаг: Отправка клавиатуры с курсами][EventID: {}]", eventId);
            botVkOperator.sendMessageWithKeyboard(userId, rateKeyboard, text);

            log.info("[Сценарий getRates][Шаг: Финиш][EventID: {}]", eventId);
        }catch (Exception e){
            log.error("Ошибка при попытке получить курс альбома в сервисе vkBotService: {}", e.getMessage());
        }
    }
}
