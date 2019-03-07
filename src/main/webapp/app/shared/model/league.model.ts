import { ISeason } from 'app/shared/model/season.model';

export interface ILeague {
    id?: number;
    name?: string;
    seasons?: ISeason[];
}

export class League implements ILeague {
    constructor(public id?: number, public name?: string, public seasons?: ISeason[]) {}
}
