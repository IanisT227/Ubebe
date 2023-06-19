package com.exampracticelala.web.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
//@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true,exclude = "actors")
@Builder
public class MovieWithActorsDto  extends BaseDto{
    private String title;
    private int year;
    private List<ActorDto> actors;
}
