import numpy as np

import Constraints as c


def binary_puzzle_backtracking(grid, x, y, value):
    if len(grid[x]) - 1 == y and len(grid[:][y]) - 1 == x:
        print("Solved grid")
        print(grid)
        return

    while (grid[x][y] == 0 or grid[x][y] == 1) and y < len(grid[x]) - 1:
        y = y + 1

    grid = np.copy(grid)
    grid[x][y] = value
    if c.check_three_consecutive_numbers(grid[x]) or c.check_three_consecutive_numbers(grid[:][y]):
        return
    if not c.check_if_rows_are_unique(grid):
        return
    if not c.check_if_columns_are_unique(grid):
        return
    if x == len(grid[:][y]):
        if c.check_if_ones_zeros_are_equal(grid[:][y]):
            return
    if len(grid[x]) - 1 > y:
        binary_puzzle_backtracking(grid, x, y + 1, 0)
        binary_puzzle_backtracking(grid, x, y + 1, 1)

    if y == len(grid[x]) - 1:
        if c.check_if_ones_zeros_are_equal(grid[x]) and len(grid[:][y]) - 1 > x:
            binary_puzzle_backtracking(grid, x + 1, 0, 0)
            binary_puzzle_backtracking(grid, x + 1, 0, 1)
        else:
            return
