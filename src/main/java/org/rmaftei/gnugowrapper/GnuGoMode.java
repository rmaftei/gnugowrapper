package org.rmaftei.gnugowrapper;

public enum GnuGoMode {
    ASCII("ascii"), GTP("gtp");

    private String mode;

    private GnuGoMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return mode;
    }
}
