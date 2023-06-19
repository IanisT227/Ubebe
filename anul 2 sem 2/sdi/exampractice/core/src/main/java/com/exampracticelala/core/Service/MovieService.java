package com.exampracticelala.core.Service;

import com.exampracticelala.core.Domain.Movie;

import java.util.List;

public interface MovieService {
//    Movie saveMovie(int stock, String title, int year, String genre);

//    Movie updateMovie(Long id, int stock, String title, int year, String genre, Set<Long> rentals);

    List<Movie> getAllMovies();

    Movie getMovieWithActors(Long movieId);
    Movie addActor(Long movieId,Long actorId);

//    void deleteMovie(Long id);
}
