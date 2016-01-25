package edu.rosehulman.teamworkout.Users;

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
