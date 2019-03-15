package pl.mamuti.tournament.web.rest;
import pl.mamuti.tournament.domain.League;
import pl.mamuti.tournament.domain.Season;
import pl.mamuti.tournament.service.LeagueService;
import pl.mamuti.tournament.service.SeasonService;
import pl.mamuti.tournament.web.rest.errors.BadRequestAlertException;
import pl.mamuti.tournament.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing League.
 */
@RestController
@RequestMapping("/api")
public class LeagueResource {

    private final Logger log = LoggerFactory.getLogger(LeagueResource.class);

    private static final String ENTITY_NAME = "league";

    private final LeagueService leagueService;

    private final SeasonService seasonService;

    public LeagueResource(LeagueService leagueService, SeasonService seasonService) {
        this.leagueService = leagueService;
        this.seasonService = seasonService;
    }

    /**
     * POST  /leagues : Create a new league.
     *
     * @param league the league to create
     * @return the ResponseEntity with status 201 (Created) and with body the new league, or with status 400 (Bad Request) if the league has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/leagues")
    public ResponseEntity<League> createLeague(@RequestBody League league) throws URISyntaxException {
        log.debug("REST request to save League : {}", league);
        if (league.getId() != null) {
            throw new BadRequestAlertException("A new league cannot already have an ID", ENTITY_NAME, "idexists");
        }
        League result = leagueService.save(league);
        return ResponseEntity.created(new URI("/api/leagues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /leagues : Updates an existing league.
     *
     * @param league the league to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated league,
     * or with status 400 (Bad Request) if the league is not valid,
     * or with status 500 (Internal Server Error) if the league couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/leagues")
    public ResponseEntity<League> updateLeague(@RequestBody League league) throws URISyntaxException {
        log.debug("REST request to update League : {}", league);
        if (league.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        League result = leagueService.save(league);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, league.getId().toString()))
            .body(result);
    }

    /**
     * GET  /leagues : get all the leagues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of leagues in body
     */
    @GetMapping("/leagues")
    public List<League> getAllLeagues() {
        log.debug("REST request to get all Leagues");
        return leagueService.findAll();
    }

    /**
     * GET  /leagues/seasons/:id : get all the seasons fpr leagues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of leagues in body
     */
    @GetMapping("/leagues/seasons/{id}")
    public List<Season> getAllSeasonsForLeague(@PathVariable Long id) {
        log.debug("REST request to get all seasons for League");
        Optional<League> optional = leagueService.findOne(id);
        return optional
            .map(league -> new ArrayList<>(league.getSeasons()))
            .orElse(new ArrayList<>());
    }

    /**
     * GET  /leagues/:id : get the "id" league.
     *
     * @param id the id of the league to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the league, or with status 404 (Not Found)
     */
    @GetMapping("/leagues/{id}")
    public ResponseEntity<League> getLeague(@PathVariable Long id) {
        log.debug("REST request to get League : {}", id);
        Optional<League> league = leagueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(league);
    }

    /**
     * DELETE  /leagues/:id : delete the "id" league.
     *
     * @param id the id of the league to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leagues/{id}")
    public ResponseEntity<Void> deleteLeague(@PathVariable Long id) {
        log.debug("REST request to delete League : {}", id);
        leagueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
