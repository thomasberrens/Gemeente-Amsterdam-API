package org.gemeenteamsterdam.api.repositories;

import org.gemeenteamsterdam.api.entities.Choice;
import org.gemeenteamsterdam.api.entities.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameInfoRepository extends JpaRepository<GameInfo, Long> {

    GameInfo findByGameID(String gameID);
}
