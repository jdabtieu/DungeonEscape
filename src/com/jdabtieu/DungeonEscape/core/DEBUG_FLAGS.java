package com.jdabtieu.DungeonEscape.core;

public enum DEBUG_FLAGS {
    PRINT_LOCATION(true);
    private final boolean val;
    private DEBUG_FLAGS(boolean val) {
        this.val = val;
    }
    public boolean get() {
        return val;
    }
}
