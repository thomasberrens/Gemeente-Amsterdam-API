package org.gemeenteamsterdam.api.services.game;

import lombok.RequiredArgsConstructor;
import org.gemeenteamsterdam.api.dto.PlayerInfoDTO;
import org.gemeenteamsterdam.api.entities.GameInfo;
import org.gemeenteamsterdam.api.entities.PlayerInfo;
import org.gemeenteamsterdam.api.repositories.PlayerInfoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerInfoService {
    private final PlayerInfoRepository playerInfoRepository;
    private final GameInfoService gameInfoService;

    public PlayerInfo createPlayerInfo(PlayerInfoDTO playerInfoDTO) {
        final PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setUsername(playerInfoDTO.getUsername());
        playerInfo.setUuid(UUID.randomUUID().toString());
        return playerInfoRepository.save(playerInfo);
    }

    public PlayerInfo save(PlayerInfo playerInfo) {
        return playerInfoRepository.save(playerInfo);
    }

    public GameInfo bindPlayerInfo(String uuid, String gameID) {
        final PlayerInfo playerInfo = playerInfoRepository.findByUuid(uuid);

        final GameInfo gameInfo = gameInfoService.getGameInfo(gameID);

        gameInfo.setPlayerInfo(playerInfo);

        playerInfo.getGamesPlayed().add(gameInfo);

        playerInfoRepository.save(playerInfo);

        return gameInfoService.save(gameInfo);
    }

    public void deletePlayerInfo(String uuid) {
        playerInfoRepository.deleteByUuid(uuid);
    }

    public PlayerInfo getPlayerInfo(String uuid) {
        return playerInfoRepository.findByUuid(uuid);
    }

    public Iterable<PlayerInfo> getAllPlayerInfo() {
        return playerInfoRepository.findAll();
    }
}
