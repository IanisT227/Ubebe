package com.exampracticelala.core.Domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@RequiredArgsConstructor
@ToString(callSuper = true)
@Builder
public class Movie extends BaseEntity<Long> {
    @Column(name = "title", nullable = false,unique = true)
    private String title;
    @Column(name = "year", nullable = false)
    private int year;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Actor> actors = new HashSet<>();

    public List<Actor> getActors() {
        return this.actors == null ? new ArrayList<>() : this.actors.stream().toList();
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void addActors(Set<Actor> actors) {
        actors.forEach(this::addActor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Movie movie = (Movie) o;
        return getId() != null && Objects.equals(getId(), movie.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
