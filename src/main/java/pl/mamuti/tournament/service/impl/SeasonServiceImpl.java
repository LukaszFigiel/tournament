package pl.mamuti.tournament.service.impl;

import pl.mamuti.tournament.domain.Match;
import pl.mamuti.tournament.domain.Team;
import pl.mamuti.tournament.service.SeasonService;
import pl.mamuti.tournament.domain.Season;
import pl.mamuti.tournament.repository.SeasonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Season.
 */
@Service
@Transactional
public class SeasonServiceImpl implements SeasonService {

    private final Logger log = LoggerFactory.getLogger(SeasonServiceImpl.class);

    private final SeasonRepository seasonRepository;

    public SeasonServiceImpl(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    /**
     * Save a season.
     *
     * @param season the entity to save
     * @return the persisted entity
     */
    @Override
    public Season save(Season season) {
        log.debug("Request to save Season : {}", season);
        return seasonRepository.save(season);
    }

    /**
     * Get all the seasons.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Season> findAll() {
        log.debug("Request to get all Seasons");
        return seasonRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Match> findSeasonMatches(Long seasonId) {
        log.debug("Request to get all Matches for season: {}", seasonId);
        return seasonRepository
            .findById(seasonId)
            .map(season -> new ArrayList<>(season.getMatches()))
            .orElse(new ArrayList<>());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Team> findSeasonTeams(Long seasonId) {
        log.debug("Request to get all Teams for season: {}", seasonId);
        return seasonRepository
            .findById(seasonId)
            .map(season -> new ArrayList<>(season.getTeams()))
            .orElse(new ArrayList<>());
    }

    /**
     * Get one season by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Season> findOne(Long id) {
        log.debug("Request to get Season : {}", id);
        return seasonRepository.findById(id);
    }

    /**
     * Delete the season by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Season : {}", id);
        seasonRepository.deleteById(id);
    }
}
