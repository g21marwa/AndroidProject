package com.example.dynamicgraphics;

class GameInfo {
    private String ID;
    private String name;
    private String type;
    private String location;
    private String company;
    private String category;
    private int size;
    private int cost;
    private Auxdata auxdata;
    public GameInfo(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Auxdata getAuxdata() {
        return auxdata;
    }

    public void setAuxdata(Auxdata auxdata) {
        this.auxdata = auxdata;
    }
}
