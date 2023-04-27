package org.gemeenteamsterdam.api.services.choice;

import org.gemeenteamsterdam.api.dto.ChoiceDTO;
import org.gemeenteamsterdam.api.entities.Choice;

import java.util.HashMap;
import java.util.List;

public interface ChoiceService {
    Choice save(ChoiceDTO choiceDTO);
    Iterable<Choice> findAll();

    HashMap<String, List<Choice>> findAllByUsers();
}
