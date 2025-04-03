package com.usareboot.back.client;

import com.usareboot.back.client.config.vk.VkMultipartConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

//@FeignClient(name = "vkUploadClient", url = "https://api.vk.com/method", configuration = VkMultipartConfig.class)
public interface VkUploadClient {
   /* @PostMapping(value="/photos.getWallUploadServer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadPhoto(@RequestPart("photo") MultipartFile photo);*/
}