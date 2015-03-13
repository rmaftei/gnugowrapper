package org.rmaftei.gnugowrapper;

public enum Boardsize {
    SMALL(9), MEDIUM(13), BIG(19);

    private int size = 0;

    private Boardsize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}
