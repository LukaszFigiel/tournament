import { ITeam } from 'app/shared/model/team.model';
import { IMatch } from 'app/shared/model/match.model';
import { ILeague } from 'app/shared/model/league.model';

export interface ISeason {
    id?: number;
    number?: number;
    teams?: ITeam[];
    matches?: IMatch[];
    league?: ILeague;
}

export class Season implements ISeason {
    constructor(public id?: number, public number?: number, public teams?: ITeam[], public matches?: IMatch[], public league?: ILeague) {}
}
