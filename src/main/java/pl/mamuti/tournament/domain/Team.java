package pl.mamuti.tournament.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("teams")
    private Player player1;

    @ManyToOne
    @JsonIgnoreProperties("teams")
    private Player player2;

    @ManyToOne
    @JsonIgnoreProperties("teams")
    private Tournament tournament;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Team player1(Player player) {
        this.player1 = player;
        return this;
    }

    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Team player2(Player player) {
        this.player2 = player;
        return this;
    }

    public void setPlayer2(Player player) {
        this.player2 = player;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Team tournament(Tournament tournament) {
        this.tournament = tournament;
        return this;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        if (team.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
