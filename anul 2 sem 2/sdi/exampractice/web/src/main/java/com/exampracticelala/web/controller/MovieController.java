package com.exampracticelala.web.controller;

import com.exampracticelala.core.Domain.Movie;
import com.exampracticelala.core.Service.MovieService;
import com.exampracticelala.web.converter.MovieConverter;
import com.exampracticelala.web.converter.MovieWithActorsConverter;
import com.exampracticelala.web.dto.MovieDto;
import com.exampracticelala.web.dto.MovieWithActorsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;
    @Autowired
    private MovieWithActorsConverter movieWithActorsConverter;

    @RequestMapping(value = "/movies/{title}")
    @Transactional
    List<MovieDto> getAllMoviesByTitle(@PathVariable String title) {
        List<Movie> moviesByTitle = movieService.getAllMovies().stream().filter(m -> m.getTitle().equals(title)).toList();
        return new ArrayList<>(movieConverter.convertModelsToDtos(moviesByTitle));
    }

    @RequestMapping(value = "/movies")
    @Transactional
    List<MovieDto> getAllMovies() {
        return new ArrayList<>(
                movieConverter.convertModelsToDtos(
                        movieService.getAllMovies()));
    }

    @RequestMapping(value = "/movieActors/{movieId}")
    MovieWithActorsDto getAllMoviesWithActors(@PathVariable Long movieId) {
        return movieWithActorsConverter.convertModelToDto(movieService.getMovieWithActors(movieId));
    }

    @RequestMapping(value = "/movies/{movieId}/{actorId}",method = RequestMethod.GET)
    MovieWithActorsDto addActor(@PathVariable Long movieId, @PathVariable Long actorId) {
        logger.info("add actor!!!! ---- movieid: " + movieId + "\n actorid: " + actorId);
        var result = movieService.addActor(movieId, actorId);
        logger.info(result.toString());
        return movieWithActorsConverter.convertModelToDto(result);
    }
}
