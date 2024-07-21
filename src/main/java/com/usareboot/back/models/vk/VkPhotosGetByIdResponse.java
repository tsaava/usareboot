package com.usareboot.back.models.vk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkPhotosGetByIdResponse {
  List<VkPhotoSaveDTO> response;
}
