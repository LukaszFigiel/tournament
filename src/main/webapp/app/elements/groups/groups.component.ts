import { Component, OnInit, OnDestroy, Input, EventEmitter } from '@angular/core';
import { ISeason } from '../../shared/model/season.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { AccountService } from '../../core/auth/account.service';
import { Group } from './group.model';
import { MatchService } from '../../entities/match/match.service';
import { TeamService } from '../../entities/team/team.service';
import { forkJoin, Subscription } from 'rxjs/index';
import { SeasonService } from '../../entities/season/season.service';

@Component({
    selector: 'jhi-groups',
    templateUrl: './groups.component.html'
})
export class GroupsComponent implements OnInit, OnDestroy {
    @Input() season: ISeason;
    groups: Group[];
    eventSubscriber: Subscription;
    currentAccount: any;

    constructor(
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected seasonService: SeasonService,
        protected matchService: MatchService,
        protected teamService: TeamService
    ) {}

    private prepareGroups(): void {
        this.groups = [];
        if (this.season.teams) {
            this.season.teams.forEach(team => {
                this.getGroup(team.group).addTeam(team);
            });
        }
        this.groups.forEach(group => group.sortTeams());
    }

    private getGroup(groupName: string): Group {
        const gr = this.groups.filter(group => group.name === groupName);
        if (gr && gr[0]) {
            return gr[0];
        } else {
            const newGroup = new Group(groupName, [], this.season.matches);
            this.groups.push(newGroup);
            return newGroup;
        }
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSeason();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSeason() {
        this.eventSubscriber = this.eventManager.subscribe('seasonDataReloaded', () => {
            this.prepareGroups();
        });
    }
}
