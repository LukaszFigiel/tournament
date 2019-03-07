import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISeason } from 'app/shared/model/season.model';
import { SeasonService } from './season.service';
import { ILeague } from 'app/shared/model/league.model';
import { LeagueService } from 'app/entities/league';

@Component({
    selector: 'jhi-season-update',
    templateUrl: './season-update.component.html'
})
export class SeasonUpdateComponent implements OnInit {
    season: ISeason;
    isSaving: boolean;

    leagues: ILeague[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected seasonService: SeasonService,
        protected leagueService: LeagueService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ season }) => {
            this.season = season;
        });
        this.leagueService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILeague[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILeague[]>) => response.body)
            )
            .subscribe((res: ILeague[]) => (this.leagues = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.season.id !== undefined) {
            this.subscribeToSaveResponse(this.seasonService.update(this.season));
        } else {
            this.subscribeToSaveResponse(this.seasonService.create(this.season));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeason>>) {
        result.subscribe((res: HttpResponse<ISeason>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLeagueById(index: number, item: ILeague) {
        return item.id;
    }
}
