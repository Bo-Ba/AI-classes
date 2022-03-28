import numpy as np


def load():
    lines = []
    with open("Resources/binary_6x6") as file:
        while line := file.readline():
            lines.append(line.rstrip())
    grid = []
    row = []

    for line in lines:
        for char in line:
            if char == 'x':
                char = '-1'
            row.append(char)
        row = list(map(int, row))
        grid.append(row)
        row = []
    return np.asarray(grid)
