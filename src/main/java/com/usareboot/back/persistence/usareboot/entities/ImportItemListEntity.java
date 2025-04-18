package com.usareboot.back.persistence.usareboot.entities;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Objects;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "vpImportDataInList", procedureName = "vp_import_data_in_list", resultClasses = {ImportItemListEntity.class},
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "data", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "albom_name", type = String.class)
                })
})

@Entity
@Table(name = "import_item_list", catalog = "usareboot")
public class ImportItemListEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "import_item_list_id")
    private long importItemListId;
    @Basic
    @Column(name = "client")
    private String client;
    @Basic
    @Column(name = "client_id")
    private long clientId;
    @Basic
    @Column(name = "vikup")
    private String vikup;
    @Basic
    @Column(name = "razdacha")
    private String razdacha;
    @Basic
    @Column(name = "pack")
    private Boolean pack;
    @Basic
    @Column(name = "note")
    private Boolean note;
    @Basic
    @Column(name = "sender")
    private Boolean sender;
    @Basic
    @Column(name = "date")
    private String date;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "fio")
    private String fio;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "num_order")
    private String numOrder;
    @Basic
    @Column(name = "isdownload")
    private BigInteger isdownload;
    @Basic
    @Column(name = "item_color")
    private String itemColor;
    @Basic
    @Column(name = "item_size")
    private String itemSize;
    @Basic
    @Column(name = "item_weight")
    private Double itemWeight;
    @Basic
    @Column(name = "item_name")
    private String itemName;
    @Basic
    @Column(name = "item_count")
    private Integer itemCount;
    @Basic
    @Column(name = "sp_help_id")
    private String spHelpId;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(name = "item_cost")
    private Double itemCost;

    public long getImportItemListId() {
        return importItemListId;
    }

    public void setImportItemListId(long importItemListId) {
        this.importItemListId = importItemListId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getVikup() {
        return vikup;
    }

    public void setVikup(String vikup) {
        this.vikup = vikup;
    }

    public String getRazdacha() {
        return razdacha;
    }

    public void setRazdacha(String razdacha) {
        this.razdacha = razdacha;
    }

    public Boolean getPack() {
        return pack;
    }

    public void setPack(Boolean pack) {
        this.pack = pack;
    }

    public Boolean getNote() {
        return note;
    }

    public void setNote(Boolean note) {
        this.note = note;
    }

    public Boolean getSender() {
        return sender;
    }

    public void setSender(Boolean sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumOrder() {
        return numOrder;
    }

    public void setNumOrder(String numOrder) {
        this.numOrder = numOrder;
    }

    public BigInteger getIsdownload() {
        return isdownload;
    }

    public void setIsdownload(BigInteger isdownload) {
        this.isdownload = isdownload;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public Double getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(Double itemWeight) {
        this.itemWeight = itemWeight;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getSpHelpId() {
        return spHelpId;
    }

    public void setSpHelpId(String spHelpId) {
        this.spHelpId = spHelpId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportItemListEntity that = (ImportItemListEntity) o;
        return importItemListId == that.importItemListId && clientId == that.clientId && Objects.equals(client, that.client) && Objects.equals(vikup, that.vikup) && Objects.equals(razdacha, that.razdacha) && Objects.equals(pack, that.pack) && Objects.equals(note, that.note) && Objects.equals(sender, that.sender) && Objects.equals(date, that.date) && Objects.equals(address, that.address) && Objects.equals(fio, that.fio) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(status, that.status) && Objects.equals(numOrder, that.numOrder) && Objects.equals(isdownload, that.isdownload) && Objects.equals(itemColor, that.itemColor) && Objects.equals(itemSize, that.itemSize) && Objects.equals(itemWeight, that.itemWeight) && Objects.equals(itemName, that.itemName) && Objects.equals(itemCount, that.itemCount) && Objects.equals(spHelpId, that.spHelpId) && Objects.equals(comment, that.comment) && Objects.equals(itemCost, that.itemCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(importItemListId, client, clientId, vikup, razdacha, pack, note, sender, date, address, fio, phone, email, status, numOrder, isdownload, itemColor, itemSize, itemWeight, itemName, itemCount, spHelpId, comment, itemCost);
    }
}
