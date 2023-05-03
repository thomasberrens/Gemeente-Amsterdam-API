package org.gemeenteamsterdam.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GameInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    private String gameID;

    @OneToMany(mappedBy = "gameID")
    @OrderColumn(name = "choice_order")
    private List<Choice> choices;

    @ManyToOne
    @JoinColumn(name = "player_info_id")
    @JsonIgnore
    private PlayerInfo playerInfo;

}
