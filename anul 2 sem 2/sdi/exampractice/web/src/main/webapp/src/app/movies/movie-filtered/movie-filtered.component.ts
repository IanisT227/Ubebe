import {Component, OnInit} from '@angular/core';
import {Movie} from "../shared/movie.model";
import {FormControl, FormGroup} from "@angular/forms";
import {MovieService} from "../shared/movie.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-movie-filtered',
  templateUrl: './movie-filtered.component.html',
  styleUrls: ['./movie-filtered.component.css']
})
export class MovieFilteredComponent implements OnInit {
  errorMessage: string;
  moviesFiltered: Array<Movie>;
  selectedMovie: Movie;
  filteringForm: FormGroup;


  constructor(private movieService: MovieService,
              private router: Router) {
  }

  ngOnInit(): void {
    // this.getMovies();
    this.initForm();
  }

  initForm(): void {
    this.filteringForm = new FormGroup({
      titleFiltering: new FormControl(''),
    });
  }

  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
  }

  gotoDetail(): void {
    this.router.navigate(['/movieapp/movie/detail', this.selectedMovie.id]);
  }

  filterMovies() {
    this.movieService.getMoviesFiltered(this.filteringForm.value["titleFiltering"])
      .subscribe(
        moviesFiltered => this.moviesFiltered = moviesFiltered,
        error => this.errorMessage = <any>error
      );
  }
}
