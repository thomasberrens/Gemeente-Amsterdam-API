package org.gemeenteamsterdam.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PlayerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String uuid;

    @OneToMany(mappedBy = "playerInfo")
    @OrderColumn(name = "game_order")
    private List<GameInfo> gamesPlayed;

}
