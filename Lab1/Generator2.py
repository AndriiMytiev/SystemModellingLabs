import random
import numpy as np
import Functions


class Generator2:
    def __init__(self, alpha, sigma, num_of_values):
        self.alpha = alpha
        self.sigma = sigma
        self.num_of_values = num_of_values

    def generate(self):
        x_array = np.array([])

        for i in range(0, self.num_of_values):
            m = 0
            for i in range(0, 12):
                m += random.random()
            m -= 6
            x_array = np.append(x_array, self.sigma * m + self.alpha)
        return x_array

    def result2(self, intervals):
        array = self.generate()
        Functions.alpha_func = self.alpha
        Functions.sigma_func = self.sigma
        genNum = 2
        Functions.statistics(intervals, array, genNum)
