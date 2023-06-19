package com.exampracticelala.core.Domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class Actor extends BaseEntity<Long> {
    @Column(name = "name", nullable = false,unique = true)
    private String name;
    @Column(name = "rating", nullable = false)
    private int rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Actor actor = (Actor) o;
        return getId() != null && Objects.equals(getId(), actor.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
