package org.gemeenteamsterdam.api.services.choice;

import lombok.RequiredArgsConstructor;
import org.gemeenteamsterdam.api.dto.ChoiceDTO;
import org.gemeenteamsterdam.api.entities.Choice;
import org.gemeenteamsterdam.api.repositories.ChoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoiceServiceImpl implements ChoiceService{
    private final ChoiceRepository choiceRepository;
    @Override
    public Choice save(ChoiceDTO choiceDTO) {
        final Choice choice = new Choice();
        choice.setChoice(choiceDTO.getChoice());
        choice.setScore(choiceDTO.getScore());
        choice.setUsername(choiceDTO.getUsername());
        choice.setScenario(choiceDTO.getScenario());

        return choiceRepository.save(choice);
    }

    @Override
    public Iterable<Choice> findAll() {
        return choiceRepository.findAll();
    }

    @Override
    public HashMap<String, List<Choice>> findAllByUsers() {
        final Iterable<Choice> choices = choiceRepository.findAll();

        // create hashmap with username as key and list of choices as value
        final HashMap<String, List<Choice>> choicesByUsers = new HashMap<String, List<Choice>>();

        // loop through all choices
        for (final Choice choice : choices) {
            // if the username is not yet in the hashmap, add it
            if (!choicesByUsers.containsKey(choice.getUsername())) {
                final List<Choice> userChoices = new ArrayList<>();
                userChoices.add(choice);
                choicesByUsers.put(choice.getUsername(), userChoices);
            }
            else {
                choicesByUsers.get(choice.getUsername()).add(choice);
            }
        }

        return choicesByUsers;
    }
}
