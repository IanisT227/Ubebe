import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import {MoviesComponent} from './movies/movies.component';
import {MovieDetailComponent} from './movies/movie-detail/movie-detail.component';
import {MovieListComponent} from './movies/movie-list/movie-list.component';
import {MovieService} from "./movies/shared/movie.service";
import {MovieFilteredComponent} from './movies/movie-filtered/movie-filtered.component';
import {ActorService} from "./movies/shared/actor.service";


@NgModule({
  declarations: [
    AppComponent,
    MoviesComponent,
    MovieDetailComponent,
    MovieListComponent,
    MovieFilteredComponent,


  ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        AppRoutingModule,
        ReactiveFormsModule,
    ],
  providers: [MovieService,ActorService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
