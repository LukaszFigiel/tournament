import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'player',
                loadChildren: './player/player.module#TournamentPlayerModule'
            },
            {
                path: 'team',
                loadChildren: './team/team.module#TournamentTeamModule'
            },
            {
                path: 'match',
                loadChildren: './match/match.module#TournamentMatchModule'
            },
            {
                path: 'player',
                loadChildren: './player/player.module#TournamentPlayerModule'
            },
            {
                path: 'team',
                loadChildren: './team/team.module#TournamentTeamModule'
            },
            {
                path: 'match',
                loadChildren: './match/match.module#TournamentMatchModule'
            },
            {
                path: 'league',
                loadChildren: './league/league.module#TournamentLeagueModule'
            },
            {
                path: 'season',
                loadChildren: './season/season.module#TournamentSeasonModule'
            },
            {
                path: 'player',
                loadChildren: './player/player.module#TournamentPlayerModule'
            },
            {
                path: 'team',
                loadChildren: './team/team.module#TournamentTeamModule'
            },
            {
                path: 'match',
                loadChildren: './match/match.module#TournamentMatchModule'
            },
            {
                path: 'league',
                loadChildren: './league/league.module#TournamentLeagueModule'
            },
            {
                path: 'season',
                loadChildren: './season/season.module#TournamentSeasonModule'
            },
            {
                path: 'player',
                loadChildren: './player/player.module#TournamentPlayerModule'
            },
            {
                path: 'team',
                loadChildren: './team/team.module#TournamentTeamModule'
            },
            {
                path: 'match',
                loadChildren: './match/match.module#TournamentMatchModule'
            },
            {
                path: 'league',
                loadChildren: './league/league.module#TournamentLeagueModule'
            },
            {
                path: 'season',
                loadChildren: './season/season.module#TournamentSeasonModule'
            },
            {
                path: 'player',
                loadChildren: './player/player.module#TournamentPlayerModule'
            },
            {
                path: 'team',
                loadChildren: './team/team.module#TournamentTeamModule'
            },
            {
                path: 'match',
                loadChildren: './match/match.module#TournamentMatchModule'
            },
            {
                path: 'league',
                loadChildren: './league/league.module#TournamentLeagueModule'
            },
            {
                path: 'season',
                loadChildren: './season/season.module#TournamentSeasonModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TournamentEntityModule {}
