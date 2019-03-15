import { IMatch } from '../../shared/model/match.model';

export class GroupMatches {
    matches: IMatch[] = [];

    constructor(public groupName: string) {}
}
