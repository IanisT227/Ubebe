package com.exampracticelala.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
//@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ActorDto extends BaseDto {
    private String name;
    private int rating;
}
