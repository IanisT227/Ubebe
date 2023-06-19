import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MovieDetailComponent} from "./movies/movie-detail/movie-detail.component";
import {MovieFilteredComponent} from "./movies/movie-filtered/movie-filtered.component";


const routes: Routes = [
  { path: '', redirectTo: '/movieapp/movies', pathMatch: 'full' },


  // {path: 'movies', component: MoviesComponent},
  {path: 'movieapp/movies', component: MovieFilteredComponent},
  // {path: 'moviesFiltered', component: MovieFilteredComponent},
  {path: 'movieapp/movie/detail/:id', component: MovieDetailComponent},
  // {path: 'movie-new', component: MovieNewComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
