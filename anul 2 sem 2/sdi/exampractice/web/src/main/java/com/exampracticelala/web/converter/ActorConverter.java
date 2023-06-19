package com.exampracticelala.web.converter;

import com.exampracticelala.core.Domain.Actor;
import com.exampracticelala.web.dto.ActorDto;
import org.springframework.stereotype.Component;

@Component
public class ActorConverter extends AbstractConverterBaseEntityConverter<Actor, ActorDto> {
    @Override
    public Actor convertDtoToModel(ActorDto dto) {
        var model = new Actor();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setRating(dto.getRating());
        return model;
    }

    @Override
    public ActorDto convertModelToDto(Actor actor) {
        ActorDto dto = new ActorDto(actor.getName(), actor.getRating());
        dto.setId(actor.getId());
        return dto;
    }
}
