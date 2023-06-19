import {Component, OnInit} from '@angular/core';
import {Movie} from "../shared/movie.model";
import {Router} from "@angular/router";
import {MovieService} from "../shared/movie.service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  moduleId: module.id,
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {
  errorMessage: string;
  movies: Array<Movie>;
  moviesFiltered: Array<Movie>;
  selectedMovie: Movie;
  addingForm: FormGroup;
  filteringForm: FormGroup;

  constructor(private movieService: MovieService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getMovies();
    this.initForm();
  }

  initForm(): void {
    /** field1, textArea1, textArea2 are form control instances */
    this.addingForm = new FormGroup({
      stock: new FormControl(''),
      title: new FormControl(''),
      year: new FormControl(''),
      genre: new FormControl(''),
    });
    this.filteringForm = new FormGroup({
      titleFiltering: new FormControl(''),
    });
  }

  getMovies() {
    this.movieService.getMovies()
      .subscribe(
        movies => this.movies = movies,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
  }

  gotoDetail(): void {
    this.router.navigate(['/movies/detail', this.selectedMovie.id]);
  }

  // refresh(): void/ {
  //   window.location.reload();
  // }

  addMovie() {
    this.movieService.save(this.addingForm.value["title"], this.addingForm.value["year"])
      .subscribe(_ => this.getMovies());
  }

  filterMovies() {
    this.movieService.getMoviesFiltered(this.filteringForm.value["titleFiltering"])
      .subscribe(
        moviesFiltered => this.moviesFiltered = moviesFiltered,
        error => this.errorMessage = <any>error
      );
  }
}
