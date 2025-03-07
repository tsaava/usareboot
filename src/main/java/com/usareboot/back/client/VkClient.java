package com.usareboot.back.client;

import com.usareboot.back.client.config.vk.VkClientConfig;
import com.usareboot.back.models.vk.VkPhotoSaveDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@FeignClient(name = "VK", configuration = VkClientConfig.class)
public interface VkClient {
    //    @RequestMapping(path=p)
//    @PostMapping(/*value = "/{id}"*/)
    @RequestMapping( method = RequestMethod.POST, consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    String uploadPhotoInVk(@RequestPart("file1") MultipartFile photo);



}
