import { IPlayer } from 'app/shared/model/player.model';
import { ITournament } from 'app/shared/model/tournament.model';

export interface ITeam {
    id?: number;
    name?: string;
    player1?: IPlayer;
    player2?: IPlayer;
    tournament?: ITournament;
}

export class Team implements ITeam {
    constructor(
        public id?: number,
        public name?: string,
        public player1?: IPlayer,
        public player2?: IPlayer,
        public tournament?: ITournament
    ) {}
}
