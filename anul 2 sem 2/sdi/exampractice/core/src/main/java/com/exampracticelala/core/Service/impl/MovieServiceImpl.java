package com.exampracticelala.core.Service.impl;

import com.exampracticelala.core.Domain.Actor;
import com.exampracticelala.core.Domain.Movie;
import com.exampracticelala.core.Repository.MovieAppRepository;
import com.exampracticelala.core.Service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    private static final Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);
    @Autowired
    private MovieAppRepository<Movie, Long> movieRepository;

    @Autowired
    private MovieAppRepository<Actor, Long> actorRepository;
    @Transactional
    public List<Movie> getAllMovies() {
        List<Movie> movies = this.movieRepository.findAll();
        movies.forEach(c -> logger.info(c.toString()));
        return movies;
    }

    @Override
    @Transactional
    public Movie getMovieWithActors(Long movieId) {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow();
        List<Actor> actors = movie.getActors();
        movie.setActors(new HashSet<>(actors));
        return movie;
    }

    @Override
    @Transactional
    public Movie addActor(Long movieId, Long actorId) {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow();
        Movie oldMovie = Movie.builder().title(movie.getTitle()).year(movie.getYear()).actors(new HashSet<>(movie.getActors())).build();
        Actor actor = this.actorRepository.findById(actorId).orElseThrow();
        movie.addActor(actor);
        return oldMovie;
    }
}
