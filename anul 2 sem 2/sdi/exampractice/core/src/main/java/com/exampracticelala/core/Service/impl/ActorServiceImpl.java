package com.exampracticelala.core.Service.impl;

import com.exampracticelala.core.Domain.Actor;
import com.exampracticelala.core.Domain.Movie;
import com.exampracticelala.core.Repository.jpa.ActorRepository;
import com.exampracticelala.core.Repository.jpa.MovieRepository;
import com.exampracticelala.core.Service.ActorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ActorServiceImpl implements ActorService {
    private static final Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Actor> getAllActors() {
        List<Actor> actors = this.actorRepository.findAll();
        actors.forEach(a -> logger.info(a.toString()));
        return actors;
    }

    private boolean isActorInMovie(Actor actor, Movie movie) {
        List<Actor> actors = movie.getActors();
        for (Actor a : actors) {
            if (a.getId().equals(actor.getId()))
                return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<Actor> getAllAvailableActors() {
        List<Actor> actors = this.actorRepository.findAll();
        List<Actor> available = new ArrayList<>();
        List<Movie> movies = movieRepository.findAll();
        boolean isInMovie;
        for (Actor actor : actors) {
            isInMovie=false;
            for (Movie movie : movies)
                if (isActorInMovie(actor, movie)) {
                    isInMovie=true;
                    break;
                }
            if(!isInMovie)
                available.add(actor);
        }
        return available;
    }
//    @Override
//    public List<Actor> getPaginated(int pageNumber) {
//        Pageable pageable = new PageRequest(pageNumber, 2);
//        List<Actor> result = new ArrayList<>();
//        actorRepository.findAll(pageable).forEach(result::add);
//        return result;
//    }
//    public List<Actor> getSorted() {
//        logger.info("get sorted");
//        List<Actor> actors = this.actorRepository.findAll(Sort.by(Sort.Direction.DESC, "county"));
//        List<Actor> actors = this.actorRepository.findByOrderByCountyDesc();
//        actors.forEach(a -> logger.info(a.toString()));
//        return actors;
//    }
//

}
