import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeague } from 'app/shared/model/league.model';
import { ISeason } from '../../shared/model/season.model';

@Component({
    selector: 'jhi-league-detail',
    templateUrl: './league-detail.component.html'
})
export class LeagueDetailComponent implements OnInit {
    league: ILeague;

    getLastSeason(): ISeason {
        if (this.league && this.league.seasons) {
            this.league.seasons.sort((a: ISeason, b: ISeason) => {
                return a.number - b.number;
            });
            return this.league.seasons[0];
        }
        return null;
    }

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ league }) => {
            this.league = league;
        });
    }

    previousState() {
        window.history.back();
    }
}
