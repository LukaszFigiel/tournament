import { ITeam } from 'app/shared/model/team.model';
import { ISeason } from 'app/shared/model/season.model';

export const enum MatchStage {
    GROUP = 'GROUP',
    QUATER_FINAL = 'QUATER_FINAL',
    SEMI_FINAL = 'SEMI_FINAL',
    THIRD_PLACE = 'THIRD_PLACE',
    FINAL = 'FINAL'
}

export interface IMatch {
    id?: number;
    played?: boolean;
    stage?: MatchStage;
    team1Score?: number;
    team2Score?: number;
    team1Goals?: number;
    team2Goals?: number;
    team1?: ITeam;
    team2?: ITeam;
    season?: ISeason;
}

export class Match implements IMatch {
    constructor(
        public stage?: MatchStage,
        public team1?: ITeam,
        public team2?: ITeam,
        public season?: ISeason,
        public id?: number,
        public played?: boolean,
        public team1Score?: number,
        public team2Score?: number,
        public team1Goals?: number,
        public team2Goals?: number
    ) {
        this.played = this.played || false;
    }
}
