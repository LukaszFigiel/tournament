package pl.mamuti.tournament.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tournament.
 */
@Entity
@Table(name = "tournament")
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "tournament")
    private Set<Team> teams = new HashSet<>();
    @OneToMany(mappedBy = "tournament")
    private Set<Match> matches = new HashSet<>();
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

    public Tournament name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Tournament teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Tournament addTeams(Team team) {
        this.teams.add(team);
        team.setTournament(this);
        return this;
    }

    public Tournament removeTeams(Team team) {
        this.teams.remove(team);
        team.setTournament(null);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public Tournament matches(Set<Match> matches) {
        this.matches = matches;
        return this;
    }

    public Tournament addMatches(Match match) {
        this.matches.add(match);
        match.setTournament(this);
        return this;
    }

    public Tournament removeMatches(Match match) {
        this.matches.remove(match);
        match.setTournament(null);
        return this;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
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
        Tournament tournament = (Tournament) o;
        if (tournament.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tournament.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tournament{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
