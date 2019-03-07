import { ILeague } from 'app/shared/model/league.model';
import { ITeam } from 'app/shared/model/team.model';
import { IMatch } from 'app/shared/model/match.model';

export interface ISeason {
    id?: number;
    number?: number;
    league?: ILeague;
    teams?: ITeam[];
    matches?: IMatch[];
}

export class Season implements ISeason {
    constructor(public id?: number, public number?: number, public league?: ILeague, public teams?: ITeam[], public matches?: IMatch[]) {}
}
