import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMatch } from 'app/shared/model/match.model';
import { MatchService } from './match.service';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';
import { ISeason } from 'app/shared/model/season.model';
import { SeasonService } from 'app/entities/season';

@Component({
    selector: 'jhi-match-update',
    templateUrl: './match-update.component.html'
})
export class MatchUpdateComponent implements OnInit {
    match: IMatch;
    isSaving: boolean;

    teams: ITeam[];

    seasons: ISeason[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected matchService: MatchService,
        protected teamService: TeamService,
        protected seasonService: SeasonService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ match }) => {
            this.match = match;
        });
        this.teamService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITeam[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITeam[]>) => response.body)
            )
            .subscribe((res: ITeam[]) => (this.teams = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.seasonService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISeason[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISeason[]>) => response.body)
            )
            .subscribe((res: ISeason[]) => (this.seasons = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.match.id !== undefined) {
            this.subscribeToSaveResponse(this.matchService.update(this.match));
        } else {
            this.subscribeToSaveResponse(this.matchService.create(this.match));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatch>>) {
        result.subscribe((res: HttpResponse<IMatch>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTeamById(index: number, item: ITeam) {
        return item.id;
    }

    trackSeasonById(index: number, item: ISeason) {
        return item.id;
    }
}
