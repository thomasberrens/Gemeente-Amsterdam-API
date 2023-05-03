package org.gemeenteamsterdam.api.controllers;

import lombok.RequiredArgsConstructor;
import org.gemeenteamsterdam.api.dto.ChoiceDTO;
import org.gemeenteamsterdam.api.dto.GameInfoDTO;
import org.gemeenteamsterdam.api.dto.PlayerInfoDTO;
import org.gemeenteamsterdam.api.entities.Choice;
import org.gemeenteamsterdam.api.entities.GameInfo;
import org.gemeenteamsterdam.api.services.choice.ChoiceService;
import org.gemeenteamsterdam.api.services.choice.ChoiceServiceImpl;
import org.gemeenteamsterdam.api.services.game.GameInfoService;
import org.gemeenteamsterdam.api.services.game.PlayerInfoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gameinfo")
public class GameInfoController {
    private final GameInfoService gameInfoService;

    private final ChoiceServiceImpl choiceService;

    private final PlayerInfoService playerInfoService;

    @PostMapping("/create")
    public GameInfo createGameInfo(@RequestBody GameInfoDTO gameInfoDTO) {
        final GameInfo gameInfo = gameInfoService.createGameInfo();

        playerInfoService.bindPlayerInfo(gameInfoDTO.getPlayerID(), gameInfo.getGameID());
        return gameInfo;
    }

    @GetMapping("/get/{gameID}")
    public GameInfo getGameInfo(@PathVariable String gameID) {
        return gameInfoService.getGameInfo(gameID);
    }


    @PostMapping("/add/choice")
    public void addChoice(@RequestBody ChoiceDTO choiceDTO) {

        System.out.println("Game iD: " + choiceDTO.getGameID());
        System.out.println("Choice: " + choiceDTO.getChoice());
        System.out.println("Scenario: " + choiceDTO.getScenario());


        choiceService.save(choiceDTO);
       // gameInfoService.addChoice(choice.getGameID(), choice);
    }
}
