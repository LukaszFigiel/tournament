import { IPlayer } from 'app/shared/model/player.model';
import { ISeason } from 'app/shared/model/season.model';

export interface ITeam {
    id?: number;
    name?: string;
    player1?: IPlayer;
    player2?: IPlayer;
    season?: ISeason;
}

export class Team implements ITeam {
    constructor(public id?: number, public name?: string, public player1?: IPlayer, public player2?: IPlayer, public season?: ISeason) {}
}
