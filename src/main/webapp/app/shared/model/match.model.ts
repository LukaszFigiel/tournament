import { ITeam } from 'app/shared/model/team.model';
import { ITournament } from 'app/shared/model/tournament.model';

export interface IMatch {
    id?: number;
    played?: boolean;
    team1Score?: number;
    team2Score?: number;
    team1Goals?: number;
    team2Goals?: number;
    team1?: ITeam;
    team2?: ITeam;
    tournament?: ITournament;
}

export class Match implements IMatch {
    constructor(
        public id?: number,
        public played?: boolean,
        public team1Score?: number,
        public team2Score?: number,
        public team1Goals?: number,
        public team2Goals?: number,
        public team1?: ITeam,
        public team2?: ITeam,
        public tournament?: ITournament
    ) {
        this.played = this.played || false;
    }
}
