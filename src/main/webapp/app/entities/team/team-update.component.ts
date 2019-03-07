import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from './team.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player';
import { ISeason } from 'app/shared/model/season.model';
import { SeasonService } from 'app/entities/season';

@Component({
    selector: 'jhi-team-update',
    templateUrl: './team-update.component.html'
})
export class TeamUpdateComponent implements OnInit {
    team: ITeam;
    isSaving: boolean;

    players: IPlayer[];

    seasons: ISeason[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected teamService: TeamService,
        protected playerService: PlayerService,
        protected seasonService: SeasonService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ team }) => {
            this.team = team;
        });
        this.playerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPlayer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPlayer[]>) => response.body)
            )
            .subscribe((res: IPlayer[]) => (this.players = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.team.id !== undefined) {
            this.subscribeToSaveResponse(this.teamService.update(this.team));
        } else {
            this.subscribeToSaveResponse(this.teamService.create(this.team));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>) {
        result.subscribe((res: HttpResponse<ITeam>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPlayerById(index: number, item: IPlayer) {
        return item.id;
    }

    trackSeasonById(index: number, item: ISeason) {
        return item.id;
    }
}
