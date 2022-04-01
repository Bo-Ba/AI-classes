import copy

import numpy as np
from numpy.distutils.fcompiler import none

import Constraints as c


def binary_puzzle_backtracking(grid, x, y):
    for i in range(0, 2):
        grid_copy = copy.deepcopy(grid)
        grid_copy[x][y] = i
        if check(grid_copy, x, y):
            x, y = find_next_indexes(grid, x, y)
            if x > -1:
                binary_puzzle_backtracking(grid_copy, x, y)
        else:
            return

    if len(grid[x]) - 1 == x and len(grid[:][y]) - 1 == y:
        print("good")
        print(grid)
        print()
        return


def find_next_indexes(grid, x, y):
    if y == len(grid[x]) - 1 and x == len(grid[:][y]) - 1:
        return -1, -1

    while grid[x][y] > -1:
        y = y + 1
        if y == len(grid[x]) - 1:
            if not x == len(grid[:][y]) - 1:
                y = 0
                x = x + 1
        else:
            return x, y - 1

    return x, y


def check(grid, x, y):
    if c.check_three_consecutive_numbers(grid[x]) or c.check_three_consecutive_numbers(grid[:][y]):
        return False
    if not c.check_if_rows_are_unique(grid):
        return False
    if not c.check_if_columns_are_unique(grid):
        return False
    if x == len(grid[:][y]) - 1:
        if not c.check_if_ones_zeros_are_equal(grid[:][y]):
            return False
    return True

# def binary_puzzle_backtracking(grid, x, y, value):
#     if len(grid[x]) - 1 == y and len(grid[:][y]) - 1 == x:
#         print("Solved grid")
#         print(grid)
#         return
#
#     while (grid[x][y] == 0 or grid[x][y] == 1) and y < len(grid[x]) - 1:
#         y = y + 1
#
#     grid = np.copy(grid)
#     grid[x][y] = value
#     if c.check_three_consecutive_numbers(grid[x]) or c.check_three_consecutive_numbers(grid[:][y]):
#         return
#     if not c.check_if_rows_are_unique(grid):
#         return
#     if not c.check_if_columns_are_unique(grid):
#         return
#     if x == len(grid[:][y]) - 1:
#         if not c.check_if_ones_zeros_are_equal(grid[:][y]):
#             return
#
#     if len(grid[x]) - 1 > y:
#         binary_puzzle_backtracking(grid, x, y + 1, 0)
#         binary_puzzle_backtracking(grid, x, y + 1, 1)
#         return
#
#     if y == len(grid[x]) - 1:
#         if c.check_if_ones_zeros_are_equal(grid[x]) and len(grid[:][y]) - 1 > x:
#             binary_puzzle_backtracking(grid, x + 1, 0, 0)
#             binary_puzzle_backtracking(grid, x + 1, 0, 1)
#             return
