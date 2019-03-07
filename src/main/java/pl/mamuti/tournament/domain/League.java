package pl.mamuti.tournament.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A League.
 */
@Entity
@Table(name = "league")
public class League implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "league")
    private Set<Season> seasons = new HashSet<>();
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

    public League name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public League seasons(Set<Season> seasons) {
        this.seasons = seasons;
        return this;
    }

    public League addSeason(Season season) {
        this.seasons.add(season);
        season.setLeague(this);
        return this;
    }

    public League removeSeason(Season season) {
        this.seasons.remove(season);
        season.setLeague(null);
        return this;
    }

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
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
        League league = (League) o;
        if (league.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), league.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "League{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
