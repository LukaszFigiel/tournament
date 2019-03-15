package pl.mamuti.tournament.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Match.
 */
@Entity
@Table(name = "jhi_match")
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "played")
    private Boolean played;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage")
    private MatchStage stage;

    @Column(name = "team_1_score")
    private Integer team1Score;

    @Column(name = "team_2_score")
    private Integer team2Score;

    @Column(name = "team_1_goals")
    private Integer team1Goals;

    @Column(name = "team_2_goals")
    private Integer team2Goals;

    @ManyToOne
    @JsonIgnoreProperties({"matches","season"})
    private Team team1;

    @ManyToOne
    @JsonIgnoreProperties({"matches","season"})
    private Team team2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("matches")
    private Season season;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPlayed() {
        return played;
    }

    public Match played(Boolean played) {
        this.played = played;
        return this;
    }

    public void setPlayed(Boolean played) {
        this.played = played;
    }

    public MatchStage getStage() {
        return stage;
    }

    public Match stage(MatchStage stage) {
        this.stage = stage;
        return this;
    }

    public void setStage(MatchStage stage) {
        this.stage = stage;
    }

    public Integer getTeam1Score() {
        return team1Score;
    }

    public Match team1Score(Integer team1Score) {
        this.team1Score = team1Score;
        return this;
    }

    public void setTeam1Score(Integer team1Score) {
        this.team1Score = team1Score;
    }

    public Integer getTeam2Score() {
        return team2Score;
    }

    public Match team2Score(Integer team2Score) {
        this.team2Score = team2Score;
        return this;
    }

    public void setTeam2Score(Integer team2Score) {
        this.team2Score = team2Score;
    }

    public Integer getTeam1Goals() {
        return team1Goals;
    }

    public Match team1Goals(Integer team1Goals) {
        this.team1Goals = team1Goals;
        return this;
    }

    public void setTeam1Goals(Integer team1Goals) {
        this.team1Goals = team1Goals;
    }

    public Integer getTeam2Goals() {
        return team2Goals;
    }

    public Match team2Goals(Integer team2Goals) {
        this.team2Goals = team2Goals;
        return this;
    }

    public void setTeam2Goals(Integer team2Goals) {
        this.team2Goals = team2Goals;
    }

    public Team getTeam1() {
        return team1;
    }

    public Match team1(Team team) {
        this.team1 = team;
        return this;
    }

    public void setTeam1(Team team) {
        this.team1 = team;
    }

    public Team getTeam2() {
        return team2;
    }

    public Match team2(Team team) {
        this.team2 = team;
        return this;
    }

    public void setTeam2(Team team) {
        this.team2 = team;
    }

    public Season getSeason() {
        return season;
    }

    public Match season(Season season) {
        this.season = season;
        return this;
    }

    public void setSeason(Season season) {
        this.season = season;
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
        Match match = (Match) o;
        if (match.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), match.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", played='" + isPlayed() + "'" +
            ", stage='" + getStage() + "'" +
            ", team1Score=" + getTeam1Score() +
            ", team2Score=" + getTeam2Score() +
            ", team1Goals=" + getTeam1Goals() +
            ", team2Goals=" + getTeam2Goals() +
            "}";
    }
}
