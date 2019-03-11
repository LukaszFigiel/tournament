import { Team } from '../../shared/model/team.model';
import { Match } from '../../shared/model/match.model';

export class GroupTeam {
    public matchesPlayed: number;
    public points: number;
    public goalsBalance: number;
    public name: string;
    public player1: string;
    public player2: string;

    constructor(public team: Team, public matches: Match[]) {
        this.name = team.name;
        this.player1 = team.player1 ? team.player1.name : '';
        this.player2 = team.player2 ? team.player2.name : '';
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
            const myMatches = matches.filter(m => (m.team1 && m.team1.id === this.team.id) || (m.team2 && m.team2.id === this.team.id));
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
