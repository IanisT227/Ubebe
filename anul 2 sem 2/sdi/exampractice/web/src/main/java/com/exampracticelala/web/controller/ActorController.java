package com.exampracticelala.web.controller;

import com.exampracticelala.core.Service.ActorService;
import com.exampracticelala.web.converter.ActorConverter;
import com.exampracticelala.web.dto.ActorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ActorController {
    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);
    @Autowired
    private ActorService actorService;

    @Autowired
    private ActorConverter actorConverter;

    @RequestMapping(value = "/actors")
    List<ActorDto> getAllActors() {
        logger.info("get all");
        return new ArrayList<>(
                actorConverter.convertModelsToDtos(
                        actorService.getAllActors()));
    }
    @RequestMapping(value = "/availableActors")
    List<ActorDto> getAllAvailableActors() {
        logger.info("get all available");
        return new ArrayList<>(
                actorConverter.convertModelsToDtos(
                        actorService.getAllAvailableActors()));
    }
}
