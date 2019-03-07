import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILeague } from 'app/shared/model/league.model';
import { AccountService } from 'app/core';
import { LeagueService } from './league.service';

@Component({
    selector: 'jhi-league',
    templateUrl: './league.component.html'
})
export class LeagueComponent implements OnInit, OnDestroy {
    leagues: ILeague[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected leagueService: LeagueService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.leagueService
            .query()
            .pipe(
                filter((res: HttpResponse<ILeague[]>) => res.ok),
                map((res: HttpResponse<ILeague[]>) => res.body)
            )
            .subscribe(
                (res: ILeague[]) => {
                    this.leagues = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLeagues();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILeague) {
        return item.id;
    }

    registerChangeInLeagues() {
        this.eventSubscriber = this.eventManager.subscribe('leagueListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
