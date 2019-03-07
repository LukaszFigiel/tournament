package pl.mamuti.tournament.service.impl;

import pl.mamuti.tournament.service.LeagueService;
import pl.mamuti.tournament.domain.League;
import pl.mamuti.tournament.repository.LeagueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing League.
 */
@Service
@Transactional
public class LeagueServiceImpl implements LeagueService {

    private final Logger log = LoggerFactory.getLogger(LeagueServiceImpl.class);

    private final LeagueRepository leagueRepository;

    public LeagueServiceImpl(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    /**
     * Save a league.
     *
     * @param league the entity to save
     * @return the persisted entity
     */
    @Override
    public League save(League league) {
        log.debug("Request to save League : {}", league);
        return leagueRepository.save(league);
    }

    /**
     * Get all the leagues.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<League> findAll() {
        log.debug("Request to get all Leagues");
        return leagueRepository.findAll();
    }


    /**
     * Get one league by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<League> findOne(Long id) {
        log.debug("Request to get League : {}", id);
        return leagueRepository.findById(id);
    }

    /**
     * Delete the league by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete League : {}", id);
        leagueRepository.deleteById(id);
    }
}
