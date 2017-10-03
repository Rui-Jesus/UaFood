package com.example.masterdetaildemo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ruigr on 20/09/2017.
 */

public class Item  implements Serializable{
    private static final long serialVersionUID = -1213949467658913456L;
    private String title;
    private String body;
    private String info;

    public Item(String title, String body) {
        this.title = title;
        this.body = body;
        this.info = "You're fucked";
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getInfo() { return info; }

    public void setInfo(String info) { this.info = info; }

    @Override
    public String toString() {
        return getTitle();
    }

    public static ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Refeitório de Santiago", "This is the first item"));
        items.add(new Item("Refeitório do Crasto", "This is the second item"));
        items.add(new Item("Snack-Bar/Self", "This is the third item"));
        return items;
    }
}
