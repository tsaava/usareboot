package com.usareboot.back.controllers.VK;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usareboot.back.client.VkPhotoClient;
import com.usareboot.back.client.config.vk.DynamicUrlInterceptor;
import com.usareboot.back.models.vk.VkPhotoGetListDTO;
import feign.Feign;
import feign.Target;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Import(FeignClientsConfiguration.class)
public class ConfigureFeignUrlController {
    @Value("${vk.api.version}")
    private String apiVersion;

    @Value("${vk.api.groupId}")
    private String groupId;
    private final ObjectFactory<HttpMessageConverters> messageConverters;
    private final ObjectProvider<HttpMessageConverterCustomizer> customizers;

    public ConfigureFeignUrlController(ObjectFactory<HttpMessageConverters> messageConverters, ObjectProvider<HttpMessageConverterCustomizer> customizers) {
        this.messageConverters = messageConverters;
        this.customizers = customizers;
    }

    @PostMapping(/*value = "/dynamicAlbums/{id}"*/)
    public VkPhotoGetListDTO uploadPhotoInVk(String vkPath, MultipartFile photo, MultipartFile photo2, MultipartFile photo3, MultipartFile photo4) throws IOException {
        VkPhotoClient client = getVkClient(vkPath);
        String tempString = client.uploadPhotoInVk(photo, photo2, photo3, photo4);
        System.out.println("tempString: " + tempString);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(tempString);
        VkPhotoGetListDTO userDtoList = mapper.readValue(tempString, VkPhotoGetListDTO.class);
        return userDtoList;
    }

    private VkPhotoClient getVkClient(String vkPath) {
        VkPhotoClient client = Feign.builder()
                .requestInterceptor(new DynamicUrlInterceptor(() -> vkPath))
                .contract(new SpringMvcContract())
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters, customizers))
                .target(Target.EmptyTarget.create(VkPhotoClient.class));
        return client;
    }
}
