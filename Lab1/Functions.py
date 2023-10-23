import math
from typing import List
import numpy as np
import seaborn as sns
from scipy import stats
import matplotlib.pyplot as plt
import pandas as pd
import statistics as st
from scipy import integrate

lyambda_func = 0
alpha_func = 0
sigma_func = 0


def get_intervals(array, intervals):
    interval_size = (array.max() - array.min()) / intervals

    entries_list = list()
    limit_1 = array.min()

    for i in range(0, intervals):
        limit_2 = limit_1 + interval_size

        counter = 0
        for n in array:
            if limit_1 <= n < limit_2:
                counter += 1

        entries_list.append([[limit_1, limit_2], counter])
        limit_1 = limit_2

    print(pd.DataFrame(entries_list))

    return entries_list


def create_intervals_list(entries, intervals):
    interval_list = list()
    for i in range(intervals):
        interval_list.append([entries[i][0][0], entries[i][0][1]])
    return interval_list


def chi2(expected_list, observed_list, intervals, size):
    chi_2 = 0
    for i in range(intervals):
        expected = size * expected_list[i]
        chi_2 += pow(observed_list[i] - expected, 2) / expected
    return chi_2


def observedEspectedChi2(expected_list, observed_list, intervals, arr):
    observed_chi2 = chi2(expected_list, observed_list, intervals, len(arr))
    expected_chi2 = stats.chi2.ppf(1 - .05, intervals - 1)
    return observed_chi2, expected_chi2


def statistics(intervals, array, genNum):
    average = st.mean(array)
    dispersion = st.pvariance(array)
    entries = get_intervals(array, intervals)
    plot_histogram(intervals, array)
    observed_list = [i[1] for i in entries]
    expected_list = expected_values(entries, intervals, genNum, array)
    observed_chi_2, expected_chi_2 = observedEspectedChi2(expected_list, observed_list, intervals, array)

    print('\n-----Statistics-------')
    print('Average: ' + str(average))
    print('Dispersion: ' + str(dispersion))
    print('Observed chi2: ' + str(observed_chi_2))
    print('Expected chi2: ' + str(expected_chi_2))
    if observed_chi_2 < expected_chi_2:
        print('The generated sequence follows the distribution law')
    else:
        print('The generated sequence doesn`t follows the distribution law')
    print('')


def expected_values(entries, intervals, genNum, array):
    expected_list = list()
    interval_list = create_intervals_list(entries, intervals)
    for i in range(intervals):
        if genNum == 1:
            expected_list.append(gen1_func(interval_list, i))
        if genNum == 2:
            expected_list.append(gen2_func(interval_list, i))
        if genNum == 3:
            expected_list.append(gen3_func(array, interval_list, i))
    return expected_list


def gen1_func(interval_list, i):
    lyambda = lyambda_func
    return np.exp(-lyambda * interval_list[i][0]) - np.exp(-lyambda * interval_list[i][1])


def gen2_func(interval_list, i):
    alpha = alpha_func
    sigma = sigma_func
    return \
        integrate.quad(lambda x: 1 / (sigma * np.sqrt(2 * np.pi)) * np.exp(- pow((x - alpha), 2) / (2 * pow(sigma, 2))),
                       interval_list[i][0], interval_list[i][1])[0]


def gen3_func(array, interval_list, i):
    return (interval_list[i][1] - interval_list[i][0]) / (max(array) - min(array))


def plot_histogram(intervals, arr):
    df = pd.DataFrame(arr, columns=['Value'])
    sns_plot = sns.histplot(df, bins=intervals, x="Value", kde=True)
    fig = sns_plot.get_figure()
    plt.show()