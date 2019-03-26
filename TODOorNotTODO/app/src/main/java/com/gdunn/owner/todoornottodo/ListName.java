package com.gdunn.owner.todoornottodo;

public class ListName {
    int id;
    String name;

    //constructor
    public ListName(){}
    public ListName(String listName) {
        this.name = listName;
    }
    public ListName(int id,String listName) {
        this.id = id;
        this.name = listName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setListName(String name) {
        this.name = name;
    }
}
