package edu.rosehulman.laritzm1.teamworkout.Users;

import edu.rosehulman.laritzm1.teamworkout.Users.Users;

/**
 * Created by laritzm1 on 1/16/2016.
 */
public class Coach implements Users{
    private String username;
    private String password;
    private String coachID;

    public Coach(String username, String password){
        createUser(username,password);
    }

    @Override
    public void createUser(String username, String password) {
        //use firebase
    }




}
