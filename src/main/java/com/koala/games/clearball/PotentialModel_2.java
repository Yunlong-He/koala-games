package com.koala.games.clearball;

public class PotentialModel_2 {
    MoveMachine machine;
    int rows, cols;

    int block[];

    public PotentialModel_2(MoveMachine m) {
        machine = m;
        rows = m.grid.getRows();
        cols = m.grid.getColumns();
    }

    public int getPotentialValue() {
        int potential = 0;

        for (int i = 0; i < cols; i++ )
        for (int j = 0; j < rows; j++ ) {
            if (!isEmpty(i, j)) {
                potential += getPotentialValue(i, j);
            }
        }

        return potential;
    }

    private int getPotentialValue(int x, int y) {
        int color = getValue(x, y);
        int maxCount = 1;

        int[] blocks;
        int potential = 0;
        int p;

        // horizental
        blocks = new int[cols];
        for (int i = 0 ; i < cols ; i++) {
            blocks[i] = getValue(i, y);
        }
        potential = getPotentialValue(blocks, x);

        // vertical
        blocks = new int[rows];
        for (int i = 0 ; i < rows ; i++) {
            blocks[i] = getValue(x, i);
        }
        p = getPotentialValue(blocks, y);
        if (p < potential) {
            potential = p;
        }

        // right-up <-> left-down
        int max_down = ((rows-1-y) > x) ? x : (rows-1-y);
        int max_up = ((cols-1-x) > y) ? y: (cols-1-x);
        int base_x = x - max_down;
        int base_y = y + max_down;
        blocks = new int[max_down + max_up + 1];
        for (int i = 0 ; i < blocks.length ; i++) {
            blocks[i] = getValue(base_x + i, base_y - i);
        }
        p = getPotentialValue(blocks, max_down);
        if (p < potential) {
            potential = p;
        }

        // left-up <-> right-down
        max_down = ((cols-1-x) > (rows-1-y)) ? (rows-1-y): (cols-1-x);
        max_up = (y > x) ? x : y;
        base_x = x - max_up;
        base_y = y - max_up;
        blocks = new int[max_down + max_up + 1];
        for (int i = 0 ; i < blocks.length ; i++) {
            blocks[i] = getValue(base_x + i, base_y + i);
        }
        p = getPotentialValue(blocks, max_up);
        if (p < potential) {
            potential = p;
        }

        return potential;
    }

    /**
     * Compute the potential of this point in specified direction
     *
     * 1. if in this direction, points is lower than 5, it's impossible to disappear
     * 2. the more this point has neibors, the more possible to disappear
     * 3. partners not neibor will do better influence
     * 4. enemies will do bad influence
     * 5.
     */
    private int getPotentialValue(int[] blocks, int index) {
        int potential = 0;

        if (blocks.length < 5) {
            return MoveMachine.MAX_POTENTIAL;
        }
        if ((index < 0) || (index >= blocks.length)) {
            throw new IndexOutOfBoundsException("bad index");
        }

        // get count of this body
        int count = 1;
        for (int i = index+1; i < blocks.length; i++) {
            if (blocks[i] != blocks[index]) {
                break;
            }
            count++;
        }
        for (int i = index-1; i >= 0; i--) {
            if (blocks[i] != blocks[index]) {
                break;
            }
            count++;
        }



        return potential;
    }

    public int getValue(int x, int y) {
        return machine.getValue(x, y);
    }

    public void setValue(int x, int y, int v) {
        machine.setValue(x, y, v);
    }

    public boolean isEmpty(int x, int y) {
        return machine.isEmpty(x, y);
    }


}

