package pl.mamuti.tournament.service;

import pl.mamuti.tournament.domain.Season;

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
