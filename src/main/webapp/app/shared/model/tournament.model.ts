import { ITeam } from 'app/shared/model/team.model';
import { IMatch } from 'app/shared/model/match.model';

export interface ITournament {
    id?: number;
    name?: string;
    teams?: ITeam[];
    matches?: IMatch[];
}

export class Tournament implements ITournament {
    constructor(public id?: number, public name?: string, public teams?: ITeam[], public matches?: IMatch[]) {}
}
