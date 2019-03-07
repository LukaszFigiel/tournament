import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILeague } from 'app/shared/model/league.model';
import { LeagueService } from './league.service';

@Component({
    selector: 'jhi-league-update',
    templateUrl: './league-update.component.html'
})
export class LeagueUpdateComponent implements OnInit {
    league: ILeague;
    isSaving: boolean;

    constructor(protected leagueService: LeagueService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ league }) => {
            this.league = league;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.league.id !== undefined) {
            this.subscribeToSaveResponse(this.leagueService.update(this.league));
        } else {
            this.subscribeToSaveResponse(this.leagueService.create(this.league));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeague>>) {
        result.subscribe((res: HttpResponse<ILeague>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
