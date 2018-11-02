package com.gordeev.movieland.vo;

public enum UserRole {
    USER("USER"), ADMIN("ADMIN"), GUEST("GUEST");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UserRole getByName(String name) {
        for(UserRole userRole : values()){
            if (userRole.name.equalsIgnoreCase(name)){
                return userRole;
            }
        }
        throw new IllegalArgumentException("No role with name " + name + " found");
    }
}
