package org.gemeenteamsterdam.api.repositories;

import org.gemeenteamsterdam.api.entities.PlayerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerInfoRepository extends JpaRepository<PlayerInfo, Long> {
    PlayerInfo findByUuid(String uuid);
}
