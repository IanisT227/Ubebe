import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Movie} from "./movie.model";

import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {MovieActors} from "./movieactors.model";
import {Actor} from "./actor.model";


@Injectable()
export class ActorService {
  private moviesUrl = 'http://localhost:8080/api/';

  constructor(private httpClient: HttpClient) {
  }

  getActors(): Observable<Actor[]> {
    return this.httpClient
      .get<Array<Actor>>(this.moviesUrl);
  }
  getAvailableActors(): Observable<Actor[]> {
    return this.httpClient
      .get<Array<Actor>>(this.moviesUrl+"availableActors");
  }
}
