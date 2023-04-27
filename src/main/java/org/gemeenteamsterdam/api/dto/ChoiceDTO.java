package org.gemeenteamsterdam.api.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ChoiceDTO {
    private String choice;
    private int score;
    private String username;
    private String scenario;
}
