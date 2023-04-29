package org.gemeenteamsterdam.api.controllers;

import lombok.RequiredArgsConstructor;
import org.gemeenteamsterdam.api.dto.PlayerInfoDTO;
import org.gemeenteamsterdam.api.entities.GameInfo;
import org.gemeenteamsterdam.api.entities.PlayerInfo;
import org.gemeenteamsterdam.api.services.game.PlayerInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playerinfo")
public class PlayerInfoController {
    private final PlayerInfoService playerInfoService;


    @PostMapping("/create")
    public PlayerInfo createPlayerInfo(@RequestBody PlayerInfoDTO playerInfoDTO) {
        return playerInfoService.createPlayerInfo(playerInfoDTO);
    }

    @PostMapping("/bind/{gameID}")
    public GameInfo bindPlayerInfo(@RequestBody String uuid, @PathVariable String gameID) {
        return playerInfoService.bindPlayerInfo(uuid, gameID);
    }

    @GetMapping("/get/{uuid}")
    public PlayerInfo getPlayerInfo(@PathVariable String uuid) {
        return playerInfoService.getPlayerInfo(uuid);
    }

    @GetMapping("/get/all")
    public Iterable<PlayerInfo> getAllPlayerInfo() {
        return playerInfoService.getAllPlayerInfo();
    }

    @PutMapping("/update/{uuid}")
    public PlayerInfo updatePlayerInfo(@PathVariable String uuid, @RequestBody PlayerInfoDTO playerInfoDTO) {
        final PlayerInfo playerInfo = playerInfoService.getPlayerInfo(uuid);
        playerInfo.setUsername(playerInfoDTO.getUsername());
        return playerInfoService.save(playerInfo);
    }

    @Transactional
    @DeleteMapping("/delete/{uuid}")
    public void deletePlayerInfo(@PathVariable String uuid) {
        playerInfoService.deletePlayerInfo(uuid);
    }
}
