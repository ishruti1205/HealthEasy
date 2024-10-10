package com.example.healtheasy;

public class Specialization {

    private String name;
    private int iconResId;

    public Specialization(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }
}
