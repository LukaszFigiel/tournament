export interface IPlayer {
    id?: number;
    name?: string;
}

export class Player implements IPlayer {
    constructor(public id?: number, public name?: string) {}
}
