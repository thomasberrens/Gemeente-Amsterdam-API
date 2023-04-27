package org.gemeenteamsterdam.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateAccountDTO {
    private String email;
    private String password;
    private String userName;
}
