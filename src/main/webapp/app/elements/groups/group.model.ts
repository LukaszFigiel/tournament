import { Team } from '../../shared/model/team.model';
import { Match } from '../../shared/model/match.model';
import { GroupTeam } from './groupTeam.model';

export class Group {
    teams: GroupTeam[];

    constructor(public name: string, private teamList: Team[], public matches: Match[]) {
        this.teams = teamList.map(team => new GroupTeam(team, matches));
        this.sortTeams();
    }

    addTeam(team: Team): void {
        this.teams.push(new GroupTeam(team, this.matches));
        this.sortTeams();
    }

    addMatch(match: Match): void {
        this.matches.push(match);
        this.teams.forEach(team => team.recalculatePoints(this.matches));
        this.sortTeams();
    }

    sortTeams(): void {
        this.teams.sort((a: GroupTeam, b: GroupTeam) => {
            return a.compareTo(b);
        });
    }
}
