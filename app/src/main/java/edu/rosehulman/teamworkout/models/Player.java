package edu.rosehulman.teamworkout.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by laritzm1 on 1/16/2016.
 */
public class Player  {

    private String name;
    @JsonIgnore
    private String key;

    public Player(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}