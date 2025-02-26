package com.usareboot.back.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "album_mapping_dictionary", schema = "public", catalog = "dbusareboot")
public class AlbumMappingDictionaryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "album_mapping_dictionary_id")
    private long albumMappingDictionaryId;
    @Basic
    @Column(name = "albom_part_name")
    private String albomPartName;
    @Basic
    @Column(name = "link")
    private String link;
    @Basic
    @Column(name = "active")
    private long active;

    public long getAlbumMappingDictionaryId() {
        return albumMappingDictionaryId;
    }

    public void setAlbumMappingDictionaryId(long albumMappingDictionaryId) {
        this.albumMappingDictionaryId = albumMappingDictionaryId;
    }

    public String getAlbomPartName() {
        return albomPartName;
    }

    public void setAlbomPartName(String albomPartName) {
        this.albomPartName = albomPartName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumMappingDictionaryEntity that = (AlbumMappingDictionaryEntity) o;
        return albumMappingDictionaryId == that.albumMappingDictionaryId && active == that.active && Objects.equals(albomPartName, that.albomPartName) && Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumMappingDictionaryId, albomPartName, link, active);
    }
}
