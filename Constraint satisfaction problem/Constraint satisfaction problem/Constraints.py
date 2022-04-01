import numpy as np


def check_if_every_cell_is_filled(grid):
    for i in range(len(grid)):
        for j in range(len(grid[i])):
            if grid[i][j] != 0 and grid[i][j] != 1:
                return False
    return True


def check_three_consecutive_numbers(grid_part):
    counter = 1
    previous = grid_part[0]
    if previous == -1:
        return False
    for i in range(1, len(grid_part)):
        if previous == grid_part[i] and not grid_part[i] == -1:
            counter = counter + 1
        else:
            previous = grid_part[i]
            counter = 0
        if counter == 3:
            return True
    return False


def check_if_rows_are_unique(grid):
    unq, count = np.unique(grid, axis=0, return_counts=True)
    if len(unq[count > 1]) == 0:
        return True
    else:
        for row in unq[count > 1]:
            if -1 not in row:
                return False
        return True


def check_if_columns_are_unique(grid):
    grid = np.transpose(grid)
    unq, count = np.unique(grid, axis=0, return_counts=True)
    if len(unq[count > 1]) == 0:
        return True
    else:
        for col in unq[count > 1]:
            if -1 not in col:
                return False
        return True


def check_if_ones_zeros_are_equal(grid_part):
    unique, counts = np.unique(grid_part, return_counts=True)
    count = dict(zip(unique, counts))
    if count.get(0) == count.get(1):
        return True
    return False
