package com.usareboot.back.persistence.usareboot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "album_mapping_dictionary",  catalog = "dbusareboot")
public class AlbumMappingDictionaryEntity {
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "album_mapping_dictionary_id")
    private long albumMappingDictionaryId;
    @Basic
    @Column(name = "albom_part_name")
    private String albumPartName;
    @Basic
    @Column(name = "link")
    private String link;
    @Basic
    @Column(name = "active")
    private long active;

    @Basic
    @Column(name = "currency")
    private String currency;
    @Basic
    @Column(name = "country")
    private String country;
    @Basic
    @Column(name = "url_cover")
    private String urlCover;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumMappingDictionaryEntity that = (AlbumMappingDictionaryEntity) o;
        return albumMappingDictionaryId == that.albumMappingDictionaryId && active == that.active && Objects.equals(albumPartName, that.albumPartName) && Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumMappingDictionaryId, albumPartName, link, active);
    }
}
