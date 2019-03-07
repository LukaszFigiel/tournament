package pl.mamuti.tournament.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Season.
 */
@Entity
@Table(name = "season")
public class Season implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_number")
    private Integer number;

    @OneToMany(mappedBy = "season")
    private Set<Team> teams = new HashSet<>();
    @OneToMany(mappedBy = "season")
    private Set<Match> matches = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("seasons")
    private League league;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Season number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Season teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Season addTeams(Team team) {
        this.teams.add(team);
        team.setSeason(this);
        return this;
    }

    public Season removeTeams(Team team) {
        this.teams.remove(team);
        team.setSeason(null);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public Season matches(Set<Match> matches) {
        this.matches = matches;
        return this;
    }

    public Season addMatches(Match match) {
        this.matches.add(match);
        match.setSeason(this);
        return this;
    }

    public Season removeMatches(Match match) {
        this.matches.remove(match);
        match.setSeason(null);
        return this;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    public League getLeague() {
        return league;
    }

    public Season league(League league) {
        this.league = league;
        return this;
    }

    public void setLeague(League league) {
        this.league = league;
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
        Season season = (Season) o;
        if (season.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), season.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Season{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            "}";
    }
}
