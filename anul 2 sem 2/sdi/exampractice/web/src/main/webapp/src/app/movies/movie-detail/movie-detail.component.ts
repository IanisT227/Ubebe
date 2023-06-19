import {Component, Input, OnInit} from '@angular/core';
import {Movie} from "../shared/movie.model";
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from "@angular/common";
import {MovieService} from "../shared/movie.service";
import {switchMap} from "rxjs/operators";
import {MovieActors} from "../shared/movieactors.model";
import {Actor} from "../shared/actor.model";
import {ActorService} from "../shared/actor.service";

@Component({
  selector: 'app-movie-detail',
  templateUrl: './movie-detail.component.html',
  styleUrls: ['./movie-detail.component.css']
})
export class MovieDetailComponent implements OnInit {
  movieActors: MovieActors;
  availableActors: Array<Actor>;
  errorMessage: string;
  selectedActor:Actor;
  @Input() movie: Movie;
  selected(){
    this.getMovieActors();
  }
  constructor(private movieService: MovieService, private actorService: ActorService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.movieService.getMovie(+params['id'])))
      .subscribe(movie => this.movie = movie);
    this.getMovieActors();
  }

  goBack(): void {
    this.location.back();
  }

  getMovieActors() {
    this.movieService.getMovieActors(Number(this.route.snapshot.paramMap.get('id')))
      .subscribe(
        movieActors => this.movieActors = movieActors,
        error => this.errorMessage = <any>error
      );
  }

  getAvailableActors() {
    this.actorService.getAvailableActors()
      .subscribe(
        availableActors => this.availableActors = availableActors,
        error => this.errorMessage = <any>error
      );
  }

  addMoreActors() {
    this.getAvailableActors();
  }

  addSelectedActor() {
    this.movieService.addActor(Number(this.route.snapshot.paramMap.get('id')),this.selectedActor.id);
  }
}
