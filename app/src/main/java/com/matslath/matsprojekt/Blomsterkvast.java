package com.matslath.matsprojekt;

import com.google.gson.annotations.SerializedName;

public class Blomsterkvast {
    private String id;
    private String name;
    private String type;
    private String company;
    private String location;
    private String category;
    private int size;
    private int cost;
    private String auxdata;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public int getSize() {
        return size;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getAuxdata() {
        return auxdata;
    }

    @Override
    public String toString() {
        return name;
    }

}
