package pl.mamuti.tournament.web.rest;
import pl.mamuti.tournament.domain.League;
import pl.mamuti.tournament.domain.Match;
import pl.mamuti.tournament.domain.Season;
import pl.mamuti.tournament.domain.Team;
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
 * REST controller for managing Season.
 */
@RestController
@RequestMapping("/api")
public class SeasonResource {

    private final Logger log = LoggerFactory.getLogger(SeasonResource.class);

    private static final String ENTITY_NAME = "season";

    private final SeasonService seasonService;

    public SeasonResource(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    /**
     * POST  /seasons : Create a new season.
     *
     * @param season the season to create
     * @return the ResponseEntity with status 201 (Created) and with body the new season, or with status 400 (Bad Request) if the season has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seasons")
    public ResponseEntity<Season> createSeason(@RequestBody Season season) throws URISyntaxException {
        log.debug("REST request to save Season : {}", season);
        if (season.getId() != null) {
            throw new BadRequestAlertException("A new season cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Season result = seasonService.save(season);
        return ResponseEntity.created(new URI("/api/seasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seasons : Updates an existing season.
     *
     * @param season the season to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated season,
     * or with status 400 (Bad Request) if the season is not valid,
     * or with status 500 (Internal Server Error) if the season couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seasons")
    public ResponseEntity<Season> updateSeason(@RequestBody Season season) throws URISyntaxException {
        log.debug("REST request to update Season : {}", season);
        if (season.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Season result = seasonService.save(season);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, season.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seasons : get all the seasons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seasons in body
     */
    @GetMapping("/seasons")
    public List<Season> getAllSeasons() {
        log.debug("REST request to get all Seasons");
        return seasonService.findAll();
    }

    /**
     * GET  /seasons/teams/:id : get all teams for season.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of teams in body
     */
    @GetMapping("/seasons/teams/{id}")
    public List<Team> getAllTeamsForSeason(@PathVariable Long id) {
        log.debug("REST request to get all teams for Season");
        return seasonService.findSeasonTeams(id);
    }

    /**
     * GET  /seasons/teams/:id : get all matches for season.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of matches in body
     */
    @GetMapping("/seasons/matches/{id}")
    public List<Match> getAllMatchesForSeason(@PathVariable Long id) {
        log.debug("REST request to get all matches for Season");
        return seasonService.findSeasonMatches(id);
    }

    /**
     * GET  /seasons/:id : get the "id" season.
     *
     * @param id the id of the season to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the season, or with status 404 (Not Found)
     */
    @GetMapping("/seasons/{id}")
    public ResponseEntity<Season> getSeason(@PathVariable Long id) {
        log.debug("REST request to get Season : {}", id);
        Optional<Season> season = seasonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(season);
    }

    /**
     * DELETE  /seasons/:id : delete the "id" season.
     *
     * @param id the id of the season to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seasons/{id}")
    public ResponseEntity<Void> deleteSeason(@PathVariable Long id) {
        log.debug("REST request to delete Season : {}", id);
        seasonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
