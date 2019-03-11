package pl.mamuti.tournament.web.rest;

import pl.mamuti.tournament.TournamentApp;

import pl.mamuti.tournament.domain.Match;
import pl.mamuti.tournament.repository.MatchRepository;
import pl.mamuti.tournament.service.MatchService;
import pl.mamuti.tournament.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static pl.mamuti.tournament.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pl.mamuti.tournament.domain.enumeration.MatchStage;
/**
 * Test class for the MatchResource REST controller.
 *
 * @see MatchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TournamentApp.class)
public class MatchResourceIntTest {

    private static final Boolean DEFAULT_PLAYED = false;
    private static final Boolean UPDATED_PLAYED = true;

    private static final MatchStage DEFAULT_STAGE = MatchStage.GROUP;
    private static final MatchStage UPDATED_STAGE = MatchStage.QUATER_FINAL;

    private static final Integer DEFAULT_TEAM_1_SCORE = 1;
    private static final Integer UPDATED_TEAM_1_SCORE = 2;

    private static final Integer DEFAULT_TEAM_2_SCORE = 1;
    private static final Integer UPDATED_TEAM_2_SCORE = 2;

    private static final Integer DEFAULT_TEAM_1_GOALS = 1;
    private static final Integer UPDATED_TEAM_1_GOALS = 2;

    private static final Integer DEFAULT_TEAM_2_GOALS = 1;
    private static final Integer UPDATED_TEAM_2_GOALS = 2;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchService matchService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMatchMockMvc;

    private Match match;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchResource matchResource = new MatchResource(matchService);
        this.restMatchMockMvc = MockMvcBuilders.standaloneSetup(matchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Match createEntity(EntityManager em) {
        Match match = new Match()
            .played(DEFAULT_PLAYED)
            .stage(DEFAULT_STAGE)
            .team1Score(DEFAULT_TEAM_1_SCORE)
            .team2Score(DEFAULT_TEAM_2_SCORE)
            .team1Goals(DEFAULT_TEAM_1_GOALS)
            .team2Goals(DEFAULT_TEAM_2_GOALS);
        return match;
    }

    @Before
    public void initTest() {
        match = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatch() throws Exception {
        int databaseSizeBeforeCreate = matchRepository.findAll().size();

        // Create the Match
        restMatchMockMvc.perform(post("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(match)))
            .andExpect(status().isCreated());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeCreate + 1);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.isPlayed()).isEqualTo(DEFAULT_PLAYED);
        assertThat(testMatch.getStage()).isEqualTo(DEFAULT_STAGE);
        assertThat(testMatch.getTeam1Score()).isEqualTo(DEFAULT_TEAM_1_SCORE);
        assertThat(testMatch.getTeam2Score()).isEqualTo(DEFAULT_TEAM_2_SCORE);
        assertThat(testMatch.getTeam1Goals()).isEqualTo(DEFAULT_TEAM_1_GOALS);
        assertThat(testMatch.getTeam2Goals()).isEqualTo(DEFAULT_TEAM_2_GOALS);
    }

    @Test
    @Transactional
    public void createMatchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchRepository.findAll().size();

        // Create the Match with an existing ID
        match.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchMockMvc.perform(post("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(match)))
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMatches() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList
        restMatchMockMvc.perform(get("/api/matches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(match.getId().intValue())))
            .andExpect(jsonPath("$.[*].played").value(hasItem(DEFAULT_PLAYED.booleanValue())))
            .andExpect(jsonPath("$.[*].stage").value(hasItem(DEFAULT_STAGE.toString())))
            .andExpect(jsonPath("$.[*].team1Score").value(hasItem(DEFAULT_TEAM_1_SCORE)))
            .andExpect(jsonPath("$.[*].team2Score").value(hasItem(DEFAULT_TEAM_2_SCORE)))
            .andExpect(jsonPath("$.[*].team1Goals").value(hasItem(DEFAULT_TEAM_1_GOALS)))
            .andExpect(jsonPath("$.[*].team2Goals").value(hasItem(DEFAULT_TEAM_2_GOALS)));
    }
    
    @Test
    @Transactional
    public void getMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get the match
        restMatchMockMvc.perform(get("/api/matches/{id}", match.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(match.getId().intValue()))
            .andExpect(jsonPath("$.played").value(DEFAULT_PLAYED.booleanValue()))
            .andExpect(jsonPath("$.stage").value(DEFAULT_STAGE.toString()))
            .andExpect(jsonPath("$.team1Score").value(DEFAULT_TEAM_1_SCORE))
            .andExpect(jsonPath("$.team2Score").value(DEFAULT_TEAM_2_SCORE))
            .andExpect(jsonPath("$.team1Goals").value(DEFAULT_TEAM_1_GOALS))
            .andExpect(jsonPath("$.team2Goals").value(DEFAULT_TEAM_2_GOALS));
    }

    @Test
    @Transactional
    public void getNonExistingMatch() throws Exception {
        // Get the match
        restMatchMockMvc.perform(get("/api/matches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatch() throws Exception {
        // Initialize the database
        matchService.save(match);

        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Update the match
        Match updatedMatch = matchRepository.findById(match.getId()).get();
        // Disconnect from session so that the updates on updatedMatch are not directly saved in db
        em.detach(updatedMatch);
        updatedMatch
            .played(UPDATED_PLAYED)
            .stage(UPDATED_STAGE)
            .team1Score(UPDATED_TEAM_1_SCORE)
            .team2Score(UPDATED_TEAM_2_SCORE)
            .team1Goals(UPDATED_TEAM_1_GOALS)
            .team2Goals(UPDATED_TEAM_2_GOALS);

        restMatchMockMvc.perform(put("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMatch)))
            .andExpect(status().isOk());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.isPlayed()).isEqualTo(UPDATED_PLAYED);
        assertThat(testMatch.getStage()).isEqualTo(UPDATED_STAGE);
        assertThat(testMatch.getTeam1Score()).isEqualTo(UPDATED_TEAM_1_SCORE);
        assertThat(testMatch.getTeam2Score()).isEqualTo(UPDATED_TEAM_2_SCORE);
        assertThat(testMatch.getTeam1Goals()).isEqualTo(UPDATED_TEAM_1_GOALS);
        assertThat(testMatch.getTeam2Goals()).isEqualTo(UPDATED_TEAM_2_GOALS);
    }

    @Test
    @Transactional
    public void updateNonExistingMatch() throws Exception {
        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Create the Match

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchMockMvc.perform(put("/api/matches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(match)))
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMatch() throws Exception {
        // Initialize the database
        matchService.save(match);

        int databaseSizeBeforeDelete = matchRepository.findAll().size();

        // Delete the match
        restMatchMockMvc.perform(delete("/api/matches/{id}", match.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Match.class);
        Match match1 = new Match();
        match1.setId(1L);
        Match match2 = new Match();
        match2.setId(match1.getId());
        assertThat(match1).isEqualTo(match2);
        match2.setId(2L);
        assertThat(match1).isNotEqualTo(match2);
        match1.setId(null);
        assertThat(match1).isNotEqualTo(match2);
    }
}
