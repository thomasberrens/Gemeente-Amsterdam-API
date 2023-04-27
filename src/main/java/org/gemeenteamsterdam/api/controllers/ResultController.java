package org.gemeenteamsterdam.api.controllers;

import lombok.RequiredArgsConstructor;
import org.gemeenteamsterdam.api.dto.ChoiceDTO;
import org.gemeenteamsterdam.api.entities.Choice;
import org.gemeenteamsterdam.api.services.choice.ChoiceServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/result")
public class ResultController {

    private final ChoiceServiceImpl choiceService;
    @GetMapping("/test")
    public ResponseEntity<String> testResult() {
        return ResponseEntity.ok("Test result");
    }

    @PostMapping("/choice")
    public Choice postChoice(@RequestBody ChoiceDTO choiceDTO) {
        System.out.println("HALLO POSTING CHOICE");

        // debug every value in chociedto
        System.out.println("CHOICE TEXT: " + choiceDTO.getChoice());
        System.out.println("SCORE: " + choiceDTO.getScore());
        System.out.println("USERNAME: " + choiceDTO.getUsername());

        return choiceService.save(choiceDTO);
    }

    @GetMapping("/choice/all/users")
    // get all choices from every user
    public HashMap<String, List<Choice>> getAllChoicesByUsers() {
        return choiceService.findAllByUsers();
    }

    @GetMapping("/choice/all")
    public Iterable<Choice> getAllChoices() {
        return choiceService.findAll();
    }
}
