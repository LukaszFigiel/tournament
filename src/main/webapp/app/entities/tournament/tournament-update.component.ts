import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITournament } from 'app/shared/model/tournament.model';
import { TournamentService } from './tournament.service';

@Component({
    selector: 'jhi-tournament-update',
    templateUrl: './tournament-update.component.html'
})
export class TournamentUpdateComponent implements OnInit {
    tournament: ITournament;
    isSaving: boolean;

    constructor(protected tournamentService: TournamentService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tournament }) => {
            this.tournament = tournament;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tournament.id !== undefined) {
            this.subscribeToSaveResponse(this.tournamentService.update(this.tournament));
        } else {
            this.subscribeToSaveResponse(this.tournamentService.create(this.tournament));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITournament>>) {
        result.subscribe((res: HttpResponse<ITournament>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
