import random

import numpy

from Domain.Ant import Ant
from Domain.Map import Map
from Domain.Path import Path
from Utils.Utils import directions


class Controller:
    def __init__(self, screen, given_map=Map()):

        self.sensors_visibility = {}
        self.min_dist_between_sensors = {}
        self.pheromone_level_between_sensors = {}
        self.screen = screen
        self.map = given_map
        self.ant_count = 20
        self.gScore = numpy.full((given_map.n, given_map.m), numpy.inf)
        # For node n, gScore[n] is the cost of the cheapest path from start to n currently known.
        self.fScore = numpy.full((given_map.n, given_map.m), numpy.inf)
        # For node n, fScore[n] := gScore[n] + h(n). fScore[n] represents our current best guess as to
        # how short a path from start to finish can be if it goes through n.
        self.compute_sensors()

    def h(self, x, y, finalX, finalY):
        return abs(finalX - x) + abs(finalY - y)

    def best_node(self, dict_of_nodes):
        q = list(dict_of_nodes.keys())
        best_node_index = 0
        for i in range(len(q)):
            if dict_of_nodes[q[i]] < dict_of_nodes[q[best_node_index]]:
                best_node_index = i
        node = q[best_node_index]
        return node

    def reconstruct_path(self, ancestors, start, end):
        node = ancestors[end]
        path = [end]
        while node != start:
            path.append(node)
            node = ancestors[node]

        path.append(start)
        path.reverse()
        return path

    def searchAStar(self, givenMap, startX, startY, finalX, finalY, screen):
        # returns a list of moves as a list of pairs [x,y]
        start = (startX, startY)
        end = (finalX, finalY)
        visited = []

        ancestors = {}  # used to reconstruct the path
        toVisit = {start: 0}

        while len(toVisit) > 0:
            node = self.best_node(toVisit)
            del toVisit[node]
            visited.append(node)  # visited = visited U {node}

            if node == end:
                return self.reconstruct_path(ancestors, start, node)

            for direction in directions:
                x = node[0] + direction[0]
                y = node[1] + direction[1]
                if (x, y) not in visited:
                    if 0 <= x < givenMap.n and 0 <= y < givenMap.n:  # if valid
                        if givenMap.surface[x][y] == 0 or givenMap.surface[x][y] == 2:
                            # is either free space or sensor
                            if self.gScore[node[0]][node[1]] + 1 < self.gScore[x][y] or (x, y) not in toVisit:
                                self.fScore[x][y] = self.gScore[node[0]][node[1]] + 1 + self.h(x, y, finalX,
                                                                                               finalX)
                                ancestors[(x, y)] = node
                                if (x, y) not in toVisit:
                                    toVisit[(x, y)] = self.fScore[x][y]
        return []

    def compute_sensors(self):
        # for step 1
        # we check for each sensors how far they can reach (0,...,5)

        sensors = self.map.sensors
        sensors_visibility = {}
        # a map, containing a sensor and its list of how far it can reach, based on its energy level

        for sensor in sensors:
            seen = [0, 0, 0, 0, 0]
            # we initialise with 0 because 0 energy = 0 reach
            x = sensor[0]
            y = sensor[1]
            for d in directions:
                for i in range(1, 6):
                    new_x = x + d[0] * i
                    new_y = y + d[1] * i
                    if 0 <= new_x < self.map.n and 0 <= new_y < self.map.m and self.map.surface[new_x][new_y] == 0:
                        # if it is valid and is not a wall
                        seen[i - 1] += 1
                    else:
                        break
            sensors_visibility[sensor] = seen
        self.sensors_visibility = sensors_visibility

    def compute_min_distance_between_sensors(self):
        # we compute the minimum distance between each pair of sensors
        # step 2
        sensors = self.map.sensors
        for i in range(len(sensors) - 1):
            for j in range(i + 1, len(sensors)):
                # compute the aforementioned distance and path with the A*
                path = Path(
                    self.searchAStar(self.map, sensors[i][0], sensors[i][1], sensors[j][0], sensors[j][1], self.screen))
                self.min_dist_between_sensors[(sensors[i], sensors[j])] = path
                self.pheromone_level_between_sensors[(sensors[i], sensors[j])] = 0

        return self.min_dist_between_sensors

    def compute_one_ant(self):
        # iteration for one ant
        first_sensor = random.choice(self.map.sensors)
        ant = Ant(first_sensor)
        sensors_order = [first_sensor]
        for sensor in range(len(self.map.sensors) - 1):
            new_sensor = ant.ACO_shortest_path(self.min_dist_between_sensors, self.pheromone_level_between_sensors)
            if new_sensor != "No sensors left!":
                sensors_order.append(new_sensor)

        return sensors_order, ant.energy_level

    def run(self, rho=0.1, iterations=350):
        self.compute_min_distance_between_sensors()
        paths = self.min_dist_between_sensors
        best_one = []
        best_ant = -1
        for k in range(iterations):
            minimum = numpy.inf
            for ant in range(self.ant_count):
                ant_path, energy_left = self.compute_one_ant()
                # for each ant, in each iteration, we apply the ACO algorithm

                if k == iterations - 1:
                    result = []
                    # when we reach the end, we get the best result
                    for i in range(len(ant_path) - 1):
                        key = (ant_path[i], ant_path[i + 1])
                        if key not in paths:
                            key = (ant_path[i + 1], ant_path[i])
                            result.extend(paths[key].path[::-1])
                        else:
                            result.extend(paths[key].path)
                    if minimum > len(result):
                        minimum = len(result)
                        best_one = result
                        best_ant = (ant_path, energy_left)
            for key in self.pheromone_level_between_sensors:
                # we lower the pheromone level with rho after each round of ants:
                self.pheromone_level_between_sensors[key] *= rho
        self.seen_by_sensors(best_ant[0], best_ant[1])
        return best_one

    def seen_by_sensors(self, sensors_order, energy_left):
        # step 4: we decrease the energy for the sensor
        count = 0
        energy_left_for_each_sensor = [0 for sensor in range(len(sensors_order))]

        while energy_left:
            sensor = -1
            max_visibility_for_one_sensor = 0
            for i in range(len(sensors_order)):
                if energy_left_for_each_sensor[i] < 4:
                    seen_count = self.sensors_visibility[sensors_order[i]][energy_left_for_each_sensor[i]]
                    if seen_count > max_visibility_for_one_sensor:
                        max_visibility_for_one_sensor = seen_count
                        sensor = i

            if sensor == -1:
                break

            energy_left_for_each_sensor[sensor] += 1
            count += max_visibility_for_one_sensor

            energy_left -= 1

        seen_squares = set()
        for sensor in sensors_order:
            for direction in directions:
                for index in range(1, energy_left_for_each_sensor[sensors_order.index(sensor)] + 1):
                    x = sensor[0] + direction[0] * index
                    y = sensor[1] + direction[1] * index
                    if 0 <= x < self.map.n and 0 <= y < self.map.m and self.map.surface[x][y] == 0:
                        seen_squares.add((x, y))
                        self.map.surface[x][y] = 3
                    else:
                        break

        print("Sensor order:", sensors_order)

        return count, sensors_order, energy_left_for_each_sensor
