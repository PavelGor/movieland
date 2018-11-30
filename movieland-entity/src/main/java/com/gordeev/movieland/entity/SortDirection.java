package com.gordeev.movieland.entity;

public enum SortDirection {
    ASC("asc"), DESC("desc");

    private final String name;

    SortDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SortDirection getByName(String name) {
        for (SortDirection sortDirection : values()) {
            if (sortDirection.name.equalsIgnoreCase(name)) {
                return sortDirection;
            }
        }
        throw new IllegalArgumentException("No sort parameter: " + name + " found");
    }
}
