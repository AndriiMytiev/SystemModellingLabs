import random
import numpy as np
import Functions


class Generator1:
    def __init__(self, lyambda, num_of_values):
        self.lyambda = lyambda
        self.num_of_values = num_of_values

    def create_array(self):
        x_array = np.array([])
        x_array2 = []
        for i in range(0, self.num_of_values):
            ksi = random.random()
            x_array = np.append(x_array, -np.log(ksi) / self.lyambda)
            x_array2.append(-np.log(ksi) / self.lyambda)
        self.average = np.average(x_array)
        return x_array

    def result1(self, intervals):
        array = self.create_array()
        genNum = 1
        Functions.lyambda_func = self.lyambda
        Functions.statistics(intervals, array, genNum)
