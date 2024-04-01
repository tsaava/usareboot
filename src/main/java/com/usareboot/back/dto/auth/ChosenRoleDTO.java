package com.usareboot.back.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChosenRoleDTO {

    /*!ЭТО ТО ЧТО ПРИХОДИТ С ФРОНТА КОГДА ВЫБИРАЕМ РОЛЬ!!
    * ПОЭТОМУ НУЖНО НА ФРОНТЕ ДОБАВИТЬ ПОЛЕ */
    private Long roleId;
    private String roleCode;

}
