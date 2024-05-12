package com.usareboot.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.sql.Blob;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AlbumsItemsDTO {
    private long albumItemId;
    private long albumId;
    private String albumName;
    private long vkItemId;
    private long tgItemId;
    private String photoPath;
    private String description;
    private String descriptionShort;
    private BigInteger albumItemWeight;
    private String albumItemName;
    private Integer albumItemCount;
    private String dateCreate;
    private Double albumItemCost;
    private Double albumItemRate;
    private long albumItemStatus;
    private String albumItemStatusName;
    private ByteArrayResource[] photoBlob;

}
