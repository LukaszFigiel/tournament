import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ISeason } from '../../shared/model/season.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { AccountService } from '../../core/auth/account.service';
import { Group } from './group.model';

@Component({
    selector: 'jhi-groups',
    templateUrl: './groups.component.html'
})
export class GroupsComponent implements OnInit, OnDestroy {
    @Input() season: ISeason;
    groups: Group[];
    currentAccount: any;

    constructor(protected eventManager: JhiEventManager, protected accountService: AccountService) {}

    private prepareGroups(): void {
        this.groups = [];
        if (this.season.teams) {
            this.season.teams.forEach(team => {
                this.getGroup(team.group).addTeam(team);
            });
        }
    }

    private getGroup(groupName: String): Group {
        const group = this.groups.filter(group => group.name === groupName);
        if (group && group[0]) {
            return group[0];
        } else {
            const newGroup = new Group(name, [], this.season.matches);
            this.groups.push(newGroup);
            return newGroup;
        }
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.prepareGroups();
    }

    ngOnDestroy() {}
}
