package com.gordeev.movieland.entity;

public enum Currency {
    UAH("UAH"), USD("USD"), EUR("EUR");

    private final String name;

    Currency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Currency getByName(String name) {
        for (Currency currency : values()) {
            if (currency.name.equalsIgnoreCase(name)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("No currency parameter: " + name + " found");
    }


}
