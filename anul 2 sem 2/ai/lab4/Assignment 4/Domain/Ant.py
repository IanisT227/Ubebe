import random

import Domain.Path


class Ant:
    def __init__(self, starting_sensor, energy=70):
        self.path = []
        self.energy_given = []  # list of energy for each sensors
        self.visited_sensors = [starting_sensor]
        self.energy_level = energy
        """
            ACO - ALGORITHM
            * While iteration < max_nr_of_iterations:
                1. Initialisation
                2. While !(nr_of_steps_required_to_identify_optimal_solution)
                    - for ant in ant_colony:
                        + increase the partial solution by an element
                        (ant moves one step)
                        + change locally the pheromone trail based on the last
                        element added to the solution
                3. Change the pheromone trail based on the paths traversed by
                    - the best ant / all ants
                4. return solution_of_best_ant
        """

    def ACO_shortest_path(self, sensors_distance, pheromone_level, alpha=0.8, beta=1.5):
        """
        We determine by using the Ant Colony Optimisation algorithm the minimum distance path between sensors
        :param sensors_distance: the distance between sensors
        :param pheromone_level: the level of the pheromone
        :param alpha: coefficient that controls the trail importance (how many ants have visited that edge)
        :param beta: coefficient that controls the visibility importance (how close is the next node)
        :return: found = solution_of_best_ant
        """

        last_sensor = self.visited_sensors[-1]

        potential_sensors = []  # the list of sensors that can be visited after
        for sensor_pair in sensors_distance:  # (sensor0, sensor1) <=> Path
            if sensor_pair[0] == last_sensor and sensor_pair[1] not in self.visited_sensors:
                # in this pair, we want the first sensor to be the last sensor visited
                # and the second one to not be visited yet, so we can compute the path
                potential_sensors.append([sensor_pair[1], sensors_distance[sensor_pair]])
            if sensor_pair[1] == last_sensor and sensor_pair[0] not in self.visited_sensors:
                potential_sensors.append([sensor_pair[0], sensors_distance[sensor_pair]])

        probabilities = []
        for sensor in potential_sensors:
            key = (sensor[0], last_sensor)
            if key not in pheromone_level.keys():
                key = (last_sensor, sensor[0])
            distance = sensor[1].Length

            if distance <= self.energy_level:

                if pheromone_level[key] != 0:
                    # if there were pheromones used
                    probabilities.append((pheromone_level[key] ** alpha) * (1/distance**beta))
                    # (the pheromone concentration on that distance) ^ alpha
                    # multiplied with
                    # (the static probability that the ants choose that distance)^beta
                else:
                    # the pheromone level is 0
                    # this means we are on the first round of exploration, no pheromones
                    probabilities.append((1 / distance ** beta))
                    # we only append (the static probability that the ants choose that distance)^beta

        # we compute the actual list of probabilities
        s = sum(probabilities)
        if s == 0:
            return "No sensors left!"

        p = [probabilities[i] / s for i in range(len(probabilities))]
        p = [sum(p[0:i+1]) for i in range(len(p))]
        r = random.random()

        i = 0
        while r > p[i]:
            i = i+1
            # we keep incrementing i in order to find the best probability available
        found = potential_sensors[i][0]
        self.visited_sensors.append(found)

        key = (found, last_sensor)
        if key not in pheromone_level:
            key = (last_sensor, found)

        pheromone_level[key] += 1
        # we update it

        self.energy_level -= sensors_distance[key].Length
        # we subtract as much energy as the length of the path

        return found
