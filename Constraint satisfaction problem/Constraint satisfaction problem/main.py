import Constraints as c
import numpy as np
import Dataloader as dl
import Backtracking as bt
# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.


def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+F8 to toggle the breakpoint.


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    fill_test = np.asarray([[1, 1, 1], [0, 0, 0]])
    print(c.check_if_every_cell_is_filled(fill_test))

    consecutive_test = np.asarray([1, 1, 1, 1])
    print(c.check_three_consecutive_numbers(consecutive_test))

    unique_test = np.asarray([[1, 1, 1], [1, 1, 1]])
    print(c.check_if_rows_are_unique(unique_test))

    num_test = np.asarray([1, 1, 0, 0])
    print(c.check_if_ones_zeros_are_equal(num_test))

    grid = dl.load()
    bt.binary_puzzle_backtracking(grid, 0, 0, 0)


# See PyCharm help at https://www.jetbrains.com/help/pycharm/
