from Generator1 import Generator1
from Generator2 import Generator2
from Generator3 import Generator3
from scipy import stats

intervals = 15
size = 10000


def main():
    method_3()
    # print('Lyambda  = ' + str(0.5))
    # generator_1 = Generator1(0.5, size)
    # generator_1.result1(intervals)

    # print('Alpha  = ' + str(3) + '; Sigma  = ' + str(1))
    # generator_2 = Generator2(3, 1, size)
    # generator_2.result2(intervals)

    # print('a  = ' + str(13) + '; c  = ' + str(31))
    # generator_3 = Generator3(pow(5, 13), pow(2, 31), size)
    # generator_3.result3(intervals)


def method_1():
    gen_list = [0.5, 2, 5]
    for i in range(len(gen_list)):
        print('Lyambda = ' + str(gen_list[i]))
        generator_1 = Generator1(gen_list[i], size)
        generator_1.result1(intervals)


def method_2():
    gen_list = [[3, 1], [5, 2], [7, 3]]
    for i in range(len(gen_list)):
        print('Alpha = ' + str(gen_list[i][0]) + '; Sigma = ' + str(gen_list[i][1]))
        generator_2 = Generator2(gen_list[i][0], gen_list[i][1], size)
        generator_2.result2(intervals)


def method_3():
    gen_list = [[13, 31], [10, 21], [12, 25]]
    for i in range(len(gen_list)):
        print('a = ' + str(gen_list[i][0]) + '; c = ' + str(gen_list[i][1]))
        generator_3 = Generator3(pow(5, gen_list[i][0]), pow(2, gen_list[i][1]), size)
        generator_3.result3(intervals)


if __name__ == "__main__":
    main()
