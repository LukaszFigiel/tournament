import { Component, EventEmitter, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeague } from 'app/shared/model/league.model';
import { ISeason } from '../../shared/model/season.model';
import { LeagueService } from './league.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/internal/operators';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { forkJoin } from 'rxjs/index';
import { SeasonService } from '../season/season.service';

@Component({
    selector: 'jhi-league-detail',
    templateUrl: './league-detail.component.html'
})
export class LeagueDetailComponent implements OnInit {
    league: ILeague;
    dataReloadEmitter: EventEmitter<any> = new EventEmitter();

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected seasonService: SeasonService,
        protected leagueService: LeagueService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager
    ) {}

    private reloadData(season: ISeason): void {
        if (season) {
            const matchesObsevrable = this.seasonService.findAllMatches(season.id);
            const teamsObsevrable = this.seasonService.findAllTeams(season.id);

            forkJoin(matchesObsevrable, teamsObsevrable).subscribe(res => {
                season.matches = res[0].body;
                season.teams = res[1].body;
                this.dataReloadEmitter.emit(null);
                this.eventManager.broadcast({ name: 'seasonDataReloaded' });
            });
        }
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ league }) => {
            this.league = league;
            this.leagueService
                .findAllSeasons(this.league.id)
                .pipe(map((res: HttpResponse<ISeason[]>) => res.body))
                .subscribe(
                    (res: ISeason[]) => {
                        this.league.seasons = res;
                        this.reloadData(this.getLastSeason());
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });
    }

    previousState() {
        window.history.back();
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    getLastSeason(): ISeason {
        if (this.league && this.league.seasons) {
            this.league.seasons.sort((a: ISeason, b: ISeason) => {
                return a.number - b.number;
            });
            return this.league.seasons[0];
        }
        return null;
    }
}
