package com.usareboot.back.client;

import com.usareboot.back.client.config.vk.VkClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "vk-api-client",
        url = "${vk.api.base-url}",
        configuration = VkClientConfig.class
)
public interface VkApiClient {
    @PostMapping("/photos.deleteAlbum")
    String deleteAlbum(
            @RequestParam("album_id") int albumId,
            @RequestParam("group_id") int groupId,
            @RequestParam("access_token") String accessToken,
            @RequestParam("v") String version
    );

    @PostMapping("/wall.post")
    String createPost(
            @RequestParam("access_token") String accessToken,
            @RequestParam("owner_id") String ownerId,
            @RequestParam("message") String message,
            @RequestParam("from_group") String fromGroup,
            @RequestParam("v") String version
    );
}
