package org.gemeenteamsterdam.api.repositories;

import org.gemeenteamsterdam.api.entities.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
