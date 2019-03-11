import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { AccountService } from '../../core/auth/account.service';
import { Match } from '../../shared/model/match.model';

@Component({
    selector: 'jhi-matches',
    templateUrl: './matches.component.html'
})
export class MatchesComponent implements OnInit, OnDestroy {
    @Input() matches: Match[];
    currentAccount: any;

    constructor(protected eventManager: JhiEventManager, protected accountService: AccountService) {}

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {}
}
