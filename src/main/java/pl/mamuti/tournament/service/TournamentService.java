package pl.mamuti.tournament.service;

import pl.mamuti.tournament.domain.Tournament;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Tournament.
 */
public interface TournamentService {

    /**
     * Save a tournament.
     *
     * @param tournament the entity to save
     * @return the persisted entity
     */
    Tournament save(Tournament tournament);

    /**
     * Get all the tournaments.
     *
     * @return the list of entities
     */
    List<Tournament> findAll();


    /**
     * Get the "id" tournament.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Tournament> findOne(Long id);

    /**
     * Delete the "id" tournament.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
