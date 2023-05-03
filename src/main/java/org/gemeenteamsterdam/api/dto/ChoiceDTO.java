package org.gemeenteamsterdam.api.dto;

import lombok.Getter;
import lombok.Setter;
import org.gemeenteamsterdam.api.entities.GameInfo;
import org.gemeenteamsterdam.api.entities.PlayerInfo;

@Getter
@Setter
public class ChoiceDTO {
    private String choice;
    private int score;
    private String scenario;
    private String gameID;
}
