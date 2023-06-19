import {Actor} from "./actor.model";

export class MovieActors {
  constructor(title: string, year: number,actors:Actor[]) {
    this.title = title;
    this.year = year;
    this.actors=actors;
  }

  id: number;
  title: string;
  year: number;
  actors:Actor[]
}
