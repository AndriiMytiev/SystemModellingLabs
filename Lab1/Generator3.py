import random
import numpy as np
import Functions


class Generator3:
    def __init__(self, a, c, size):
        self.a = a
        self.c = c
        self.size = size

    def generate(self):
        z = self.a * random.random() % self.c

        x_array = np.array([])
        for i in range(0, self.size):
            z = self.a * z % self.c
            x_array = np.append(x_array, z / self.c)

        return x_array

    def result3(self, intervals):
        array = self.generate()
        genNum = 3
        Functions.statistics(intervals, array, genNum)
