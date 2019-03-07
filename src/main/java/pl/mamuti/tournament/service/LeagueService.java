package pl.mamuti.tournament.service;

import pl.mamuti.tournament.domain.League;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing League.
 */
public interface LeagueService {

    /**
     * Save a league.
     *
     * @param league the entity to save
     * @return the persisted entity
     */
    League save(League league);

    /**
     * Get all the leagues.
     *
     * @return the list of entities
     */
    List<League> findAll();


    /**
     * Get the "id" league.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<League> findOne(Long id);

    /**
     * Delete the "id" league.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
