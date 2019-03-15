package pl.mamuti.tournament.repository;

import pl.mamuti.tournament.domain.Season;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Season entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {

    List<Season> findAllByLeague(Long leagueId);
}
