package pl.mamuti.tournament.web.rest;
import pl.mamuti.tournament.domain.Player;
import pl.mamuti.tournament.service.PlayerService;
import pl.mamuti.tournament.web.rest.errors.BadRequestAlertException;
import pl.mamuti.tournament.web.rest.util.HeaderUtil;
import pl.mamuti.tournament.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Player.
 */
@RestController
@RequestMapping("/api")
public class PlayerResource {

    private final Logger log = LoggerFactory.getLogger(PlayerResource.class);

    private static final String ENTITY_NAME = "player";

    private final PlayerService playerService;

    public PlayerResource(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * POST  /players : Create a new player.
     *
     * @param player the player to create
     * @return the ResponseEntity with status 201 (Created) and with body the new player, or with status 400 (Bad Request) if the player has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) throws URISyntaxException {
        log.debug("REST request to save Player : {}", player);
        if (player.getId() != null) {
            throw new BadRequestAlertException("A new player cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Player result = playerService.save(player);
        return ResponseEntity.created(new URI("/api/players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /players : Updates an existing player.
     *
     * @param player the player to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated player,
     * or with status 400 (Bad Request) if the player is not valid,
     * or with status 500 (Internal Server Error) if the player couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/players")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) throws URISyntaxException {
        log.debug("REST request to update Player : {}", player);
        if (player.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Player result = playerService.save(player);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, player.getId().toString()))
            .body(result);
    }

    /**
     * GET  /players : get all the players.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of players in body
     */
    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers(Pageable pageable) {
        log.debug("REST request to get a page of Players");
        Page<Player> page = playerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/players");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /players/:id : get the "id" player.
     *
     * @param id the id of the player to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the player, or with status 404 (Not Found)
     */
    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        log.debug("REST request to get Player : {}", id);
        Optional<Player> player = playerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(player);
    }

    /**
     * DELETE  /players/:id : delete the "id" player.
     *
     * @param id the id of the player to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/players/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        log.debug("REST request to delete Player : {}", id);
        playerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}