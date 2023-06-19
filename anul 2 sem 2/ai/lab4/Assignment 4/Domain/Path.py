class Path:
    # this class keeps track of a path and of the pheromones level
    def __init__(self, path):
        self.path = path
        self.path_length = len(path)
        self.pheromone_level = []

    @property
    def Length(self):
        return self.path_length
