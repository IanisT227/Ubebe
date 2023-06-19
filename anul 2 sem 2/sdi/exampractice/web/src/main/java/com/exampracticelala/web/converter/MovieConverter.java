package com.exampracticelala.web.converter;

import com.exampracticelala.core.Domain.Actor;
import com.exampracticelala.core.Domain.Movie;
import com.exampracticelala.web.dto.ActorDto;
import com.exampracticelala.web.dto.MovieDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieConverter extends AbstractConverterBaseEntityConverter<Movie, MovieDto> {
    @Override
    public Movie convertDtoToModel(MovieDto dto) {
        var model = new Movie();
        model.setId(dto.getId());
        model.setTitle(dto.getTitle());
        model.setYear(dto.getYear());
        return model;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto dto = MovieDto.builder().title(movie.getTitle()).year(movie.getYear()).build();
        dto.setId(movie.getId());
        return dto;
    }

}
