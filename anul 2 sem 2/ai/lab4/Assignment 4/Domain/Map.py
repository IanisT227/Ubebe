import numpy
import random
import pickle
import pygame

from Utils.Utils import *


class Map:
    def __init__(self, n=20, m=20):
        self.n = n
        self.m = m
        self.surface = numpy.zeros((n, m))
        self.pheromone_level = numpy.zeros((n, m))
        self.sensors = []

    def randomMap(self, fill=0.2):
        for i in range(self.n):
            for j in range(self.m):
                if random.random() <= fill:
                    self.surface[i][j] = 1

    def saveMap(self, numFile="test.map"):
        with open(numFile, 'wb') as f:
            pickle.dump(self, f)
            f.close()

    @property
    def Sensors(self):
        return self.sensors

    @Sensors.setter
    def Sensors(self, list_of_sensors):
        self.sensors = list_of_sensors
        for sensor in list_of_sensors:
            self.surface[sensor[0]][sensor[1]] = 2

    def loadMap(self, numfile):
        with open(numfile, "rb") as f:
            dummy = pickle.load(f)
            self.n = dummy.n
            self.m = dummy.m
            self.surface = dummy.surface
            self.sensors = dummy.sensors
            self.pheromone_level = dummy.pheromone_level
            f.close()

    def image_path(self, visited):
        imagine = pygame.Surface((20*self.n, 20*self.m))
        brick = pygame.Surface((20, 20))
        brick.fill(BLUE)
        visited_brick = pygame.Surface((20, 20))
        visited_brick.fill(RED)
        seen_by_sensor = pygame.Surface((20, 20))
        seen_by_sensor.fill(GREEN)
        sensor = pygame.image.load("senzor.png").convert()
        imagine.fill(WHITE)
        for i in range(self.n):
            for j in range(self.m):
                if (i, j) in visited:
                    imagine.blit(visited_brick, (j * 20, i * 20))
                elif self.surface[i][j] == 3:
                    imagine.blit(seen_by_sensor, (j * 20, i * 20))
                elif self.surface[i][j] == 2:
                    imagine.blit(sensor, (j * 20, i * 20))
                if self.surface[i][j] == 1:
                    imagine.blit(brick, (j * 20, i * 20))
        return imagine
