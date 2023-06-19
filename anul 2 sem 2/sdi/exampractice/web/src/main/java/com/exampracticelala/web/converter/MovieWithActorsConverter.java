package com.exampracticelala.web.converter;

import com.exampracticelala.core.Domain.Actor;
import com.exampracticelala.core.Domain.Movie;
import com.exampracticelala.web.dto.ActorDto;
import com.exampracticelala.web.dto.MovieDto;
import com.exampracticelala.web.dto.MovieWithActorsDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieWithActorsConverter extends AbstractConverterBaseEntityConverter<Movie, MovieWithActorsDto> {
    @Override
    public Movie convertDtoToModel(MovieWithActorsDto dto) {
        var model = new Movie();
        model.setId(dto.getId());
        model.setTitle(dto.getTitle());
        model.setYear(dto.getYear());
        return model;
    }

    @Override
    public MovieWithActorsDto convertModelToDto(Movie movie) {
//        MovieDto dto = new MovieDto(movie.getTitle(), movie.getYear());
        List<ActorDto> actorDtos=new ArrayList<>();
        for(Actor a: movie.getActors())
        {
            ActorDto actorDto=ActorDto.builder().name(a.getName()).rating(a.getRating()).build();
            actorDto.setId(a.getId());
            actorDtos.add(actorDto);
        }
        MovieWithActorsDto dto = MovieWithActorsDto.builder().title(movie.getTitle()).year(movie.getYear()).actors(actorDtos).build();
        dto.setId(movie.getId());
        return dto;
    }

}
