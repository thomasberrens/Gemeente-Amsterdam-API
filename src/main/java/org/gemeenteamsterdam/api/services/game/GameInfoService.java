package org.gemeenteamsterdam.api.services.game;

import lombok.RequiredArgsConstructor;
import org.gemeenteamsterdam.api.dto.GameInfoDTO;
import org.gemeenteamsterdam.api.dto.PlayerInfoDTO;
import org.gemeenteamsterdam.api.entities.Choice;
import org.gemeenteamsterdam.api.entities.GameInfo;
import org.gemeenteamsterdam.api.entities.PlayerInfo;
import org.gemeenteamsterdam.api.repositories.GameInfoRepository;
import org.gemeenteamsterdam.api.services.choice.ChoiceServiceImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameInfoService {
    private final GameInfoRepository gameInfoRepository;

    public GameInfo save(GameInfo gameInfo) {
        return gameInfoRepository.save(gameInfo);
    }

    public GameInfo createGameInfo() {
        final GameInfo gameInfo = new GameInfo();
        gameInfo.setGameID(UUID.randomUUID().toString());
        return gameInfoRepository.save(gameInfo);
    }


    public GameInfo getGameInfo(String gameID) {
        return gameInfoRepository.findByGameID(gameID);
    }

    public GameInfo addChoice(String gameID, Choice newChoice) {

        System.out.println("Game iD: " + gameID);
        final GameInfo gameInfo = getGameInfo(gameID);


        gameInfo.getChoices().add(newChoice);

        return gameInfoRepository.save(gameInfo);
    }

}
