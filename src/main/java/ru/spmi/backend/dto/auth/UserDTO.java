package ru.spmi.backend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public
class UserDTO {

    //это тоже не используется, но оно потенциально полезно
    private String login;
//    private ArrayList<String> roles;

    // не использующееся поле
    private String password;
    private String token;
}
