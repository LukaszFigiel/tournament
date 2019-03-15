import { Component, OnInit, OnDestroy, Input, ViewChild } from '@angular/core';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { AccountService } from '../../core/auth/account.service';
import { IMatch, Match, MatchStage } from '../../shared/model/match.model';
import { ISeason } from '../../shared/model/season.model';
import { ITeam } from '../../shared/model/team.model';
import { MatchService } from '../../entities/match/match.service';
import { Observable, Subscription } from 'rxjs/index';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { SeasonService } from '../../entities/season/season.service';
import { TeamService } from '../../entities/team/team.service';
import { MatTabGroup } from '@angular/material';
import { GroupMatches } from './groupMatches.model';

@Component({
    selector: 'jhi-matches',
    templateUrl: './matches.component.html'
})
export class MatchesComponent implements OnInit, OnDestroy {
    @Input() season: ISeason;
    public groupMatches: GroupMatches[];
    public playOffMatches: IMatch[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected seasonService: SeasonService,
        protected matchService: MatchService,
        protected teamService: TeamService
    ) {}

    prepareMatches(): void {
        this.groupMatches = [];
        this.playOffMatches = [];
        if (this.season.matches) {
            this.season.matches.forEach(match => {
                if (match.stage === MatchStage.GROUP) {
                    const groups = this.groupMatches.filter(gr => gr.groupName === match.team1.group);
                    let group = groups[0] ? groups[0] : undefined;
                    if (group === undefined) {
                        group = new GroupMatches(match.team1.group);
                        this.groupMatches.push(group);
                    }
                    group.matches.push(match);
                } else {
                    this.playOffMatches.push(match);
                }
            });
        }
        this.groupMatches.forEach(gm => {
            gm.matches.sort((a, b) => {
                let result = a.team1.name.localeCompare(b.team1.name);
                if (result === 0) {
                    result = a.team2.name.localeCompare(b.team2.name);
                }
                return result;
            });
        });
        this.groupMatches.sort((a, b) => a.groupName.localeCompare(b.groupName));
    }

    generateGroupMatches(): void {
        const groupTeams: Map<string, ITeam[]> = new Map();
        this.season.teams.forEach(team => {
            if (!groupTeams.has(team.group)) {
                groupTeams.set(team.group, []);
            }
            groupTeams.get(team.group).push(team);
        });

        groupTeams.forEach((teams: ITeam[], key: string) => {
            teams.forEach(team => {
                const index = teams.indexOf(team);
                if (index < teams.length - 1) {
                    for (let i = index + 1; i < teams.length; i++) {
                        const match = new Match(MatchStage.GROUP, team, teams[i], this.season);
                        this.save(match);
                    }
                }
            });
        });
    }

    protected save(match: IMatch): void {
        if (match.id !== undefined) {
            this.subscribeToSaveResponse(this.matchService.update(match));
        } else {
            this.subscribeToSaveResponse(this.matchService.create(match));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatch>>) {
        result.subscribe(
            (res: HttpResponse<IMatch>) => this.onSaveSuccess(res.body),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    protected onSaveSuccess(match: IMatch): void {
        this.season.matches.push(match);
        this.prepareMatches();
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
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
            this.prepareMatches();
        });
    }
}
