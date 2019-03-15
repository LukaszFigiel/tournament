package pl.mamuti.tournament.service;

import pl.mamuti.tournament.domain.Match;
import pl.mamuti.tournament.domain.Season;
import pl.mamuti.tournament.domain.Team;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Season.
 */
public interface SeasonService {

    /**
     * Save a season.
     *
     * @param season the entity to save
     * @return the persisted entity
     */
    Season save(Season season);

    /**
     * Get all the seasons.
     *
     * @return the list of entities
     */
    List<Season> findAll();

    /**
     * Return all matches for season
     * @param seasonId
     * @return the list of matches
     */
    List<Match> findSeasonMatches(Long seasonId);

    /**
     * Return all teams for season
     * @param seasonId
     * @return the list of teams
     */
    List<Team> findSeasonTeams(Long seasonId);

    /**
     * Get the "id" season.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Season> findOne(Long id);

    /**
     * Delete the "id" season.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
