package edu.rosehulman.teamworkout.Users;

/**
 * Created by laritzm1 on 1/16/2016.
 */
public class Player implements Users {
    private String username;
    private String password;
    private String coachID;

    public Player(String username, String password, String coachID) {
        createUser(username, password);
    }

    @Override
    public void createUser(String username, String password) {
        //use firebase
    }
}