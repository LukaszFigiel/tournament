import { Team } from '../../shared/model/team.model';
import { Match } from '../../shared/model/match.model';
import { calculatePoints } from 'ngx-infinite-scroll/src/services/position-resolver';

export class GroupTeam {
    public team: Team;
    public matchesPlayed: number;
    public points: number;
    public goalsBalance: number;

    constructor(public team: Team, public matches: Match[]) {
        this.recalculatePoints(matches);
    }

    public compareTo(other: GroupTeam): number {
        let result = this.points - other.points;
        if (result === 0) {
            result = this.goalsBalance - other.goalsBalance;
        }
        return result;
    }

    public recalculatePoints(matches: Match[]): void {
        this.matchesPlayed = 0;
        this.points = 0;
        this.goalsBalance = 0;
        if (matches) {
            let myMatches = matches.filter(m => (m.team1 && m.team1.id === this.team.id) || (m.team2 && m.team2.id === this.team.id));
            myMatches.forEach(m => {
                if (m.played) {
                    if (m.team1.id === this.team.id) {
                        this.points += m.team1Score;
                        this.goalsBalance += m.team1Goals;
                        this.goalsBalance -= m.team2Goals;
                    } else {
                        this.points += m.team1Score;
                        this.goalsBalance += m.team1Goals;
                        this.goalsBalance -= m.team2Goals;
                    }
                }
            });
        }
    }
}
