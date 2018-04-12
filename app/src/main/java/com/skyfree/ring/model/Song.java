package com.skyfree.ring.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by KienBeu on 4/11/2018.
 */

public class Song extends RealmObject{

    @PrimaryKey
    private int id;

    private String name, path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Song() {

    }

    public Song(int id, String name, String path) {

        this.id = id;
        this.name = name;
        this.path = path;
    }
}
