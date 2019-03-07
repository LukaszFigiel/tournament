export interface ILeague {
    id?: number;
    name?: string;
}

export class League implements ILeague {
    constructor(public id?: number, public name?: string) {}
}
