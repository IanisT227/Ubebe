import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Movie} from "./movie.model";

import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {MovieActors} from "./movieactors.model";


@Injectable()
export class MovieService {
  private moviesUrl = 'http://localhost:8080/api/movies';

  constructor(private httpClient: HttpClient) {
  }

  getMovies(): Observable<Movie[]> {
    return this.httpClient
      .get<Array<Movie>>(this.moviesUrl);
  }

  getMovie(id: number): Observable<Movie> {
    return this.getMovies()
      .pipe(
        map(movies => movies.find(movie => movie.id === id))
      );
  }

  update(movie): Observable<Movie> {
    const url = `${this.moviesUrl}/${movie.id}`;
    return this.httpClient
      .put<Movie>(url, movie);
  }

  save(title: string, year: number): Observable<Movie> {
    const url = `${this.moviesUrl}`;
    return this.httpClient
      .post<Movie>(url, new Movie(title, year));
  }

  remove(movie): Observable<Movie> {
    const url = `${this.moviesUrl}/${movie.id}`;
    return this.httpClient
      .delete<Movie>(url);
  }

  getMoviesFiltered(title: string) {
    const url = `${this.moviesUrl}/${title}`;
    return this.httpClient
      .get<Array<Movie>>(url);
  }

  getMovieActors(id: number): Observable<MovieActors> {
    const url = `http://localhost:8080/api/movieActors/${id}`;
    return this.httpClient
      .get<MovieActors>(url);
  }
  addActor(movieId:number,actorId:number): Observable<MovieActors>{
    const url = `${this.moviesUrl}/${movieId}/${actorId}`;
    console.log(url);
    return this.httpClient.get<MovieActors>(url);
  }
}
