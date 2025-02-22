package com.usareboot.back.services.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.callback.longpoll.responses.GetLongPollServerResponse;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class VkBotLongPoll {

    private final VkApiClient vk;
    private final GroupActor actor;
    private final VkBotService botService;
    private Integer ts;

    @PostConstruct
    public void init() {
        try {
            vk.groups().getLongPollServer(actor).execute();
            startPolling();
        } catch (ApiException | ClientException e) {
            log.error(e.toString());
        }
    }

    private void startPolling() {
   /*     try {
//            GetLongPollServerResponse longPollServer = vk.groups().getLongPollServer(actor, actor.getGroupId()).execute();
            this.server = longPollServer.getServer();
            this.key = longPollServer.getKey();
            this.ts = longPollServer.getTs();

            while (true) {
                String url = String.format("%s?act=a_check&key=%s&ts=%s&wait=25", server, key, ts);
                String response = vk.getTransportClient().get(url);

                // Обработка ответа
                handleResponse(response);
            }
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        new Thread(() -> {
            try {
                while (true) {
                    MessagesGetLongPollHistoryQuery historyQuery = vk.messages()
                            .getLongPollHistory(actor)
                            .ts(ts);

                    List<Message> messages = historyQuery
                            .execute()
                            .getMessages()
                            .getItems();

                    for (Message message : messages) {
                        botService.handleMessage(message);
                    }

                    // Обновляем ts для следующего запроса
                    ts = vk.messages()
                            .getLongPollServer(actor)
                            .execute()
                            .getTs();

                    Thread.sleep(500);
                }
            } catch (ApiException | ClientException e) {
                log.error(e.toString());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error(e.toString());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
