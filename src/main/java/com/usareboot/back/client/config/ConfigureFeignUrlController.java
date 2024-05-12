package com.usareboot.back.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usareboot.back.client.VkClient;
import com.usareboot.back.dto.VkPhotoGetListDTO;
import com.usareboot.back.dto.VkPhotoSaveDTO;
import feign.Feign;
import feign.Target;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Import(FeignClientsConfiguration.class)
public class ConfigureFeignUrlController {
    private String GROUPID;//="224336762";
    private String version;//="5.139";
    private final ObjectFactory<HttpMessageConverters> messageConverters;
    private final ObjectProvider<HttpMessageConverterCustomizer> customizers;

    @Autowired
    private Environment environment;


    public ConfigureFeignUrlController(ObjectFactory<HttpMessageConverters> messageConverters, ObjectProvider<HttpMessageConverterCustomizer> customizers) {
        this.messageConverters = messageConverters;
        this.customizers = customizers;
    }

    @PostMapping(/*value = "/dynamicAlbums/{id}"*/)
    public VkPhotoGetListDTO uploadPhotoInVk(String vkPath, MultipartFile photo) throws IOException {
        VkClient client = getVkClient(vkPath);
        String tempString = client.uploadPhotoInVk(photo);
        System.out.println("tempString: " + tempString);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(tempString);
        VkPhotoGetListDTO userDtoList = mapper.readValue(tempString, VkPhotoGetListDTO.class);
        return userDtoList;
    }

    private VkClient getVkClient(String vkPath) {
        VkClient client = Feign.builder()
                .requestInterceptor(new DynamicUrlInterceptor(() -> vkPath))
                .contract(new SpringMvcContract())
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters, customizers))
                .target(Target.EmptyTarget.create(VkClient.class));
        return client;
    }

//    @PostMapping()
    public VkPhotoSaveDTO savePhotoInVk(String photo,
                                        String album_id,
                                        String server,
                                        String hash,
                                        String access_token
    ) {
        GROUPID = environment.getRequiredProperty("vk.groupId");
        version = environment.getRequiredProperty("vk.version");
        VkClient client = getVkClient("https://api.vk.com/method/photos.save");
        return client.savePhotoInVk(photo, album_id, server, hash, access_token, version, GROUPID);
    }
}
