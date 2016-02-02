package edu.rosehulman.teamworkout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rosehulman.rosefire.RosefireAuth;
import edu.rosehulman.teamworkout.Fragments.CoachSwitchFragment;
import edu.rosehulman.teamworkout.Fragments.CreateWorkoutFragment;
import edu.rosehulman.teamworkout.Fragments.LoginFragment;
import edu.rosehulman.teamworkout.Fragments.TodayWorkoutFragment;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginListener, TodayWorkoutFragment.OnLogoutListener, GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {
    private Firebase mFirebase;
    public static List<WorkoutModel> allWorkouts = new ArrayList<>();
    private WebView mTwitterView;
    private Twitter mTwitter;
    public static final int RC_TWITTER_LOGIN = 2;
    public static String USER_AUTH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Firebase.setAndroidContext(this);
        }
        mFirebase = new Firebase(Constants.FIREBASE_URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(mFirebase.getAuth() == null || isExpired(mFirebase.getAuth())){
            switchToLoginFragment();

        }else {
            switchToPasswordFragment(Constants.FIREBASE_URL + "/users" + "/"+ mFirebase.getAuth().getUid());
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment switchTo = null;
        if (id == R.id.coach_switch) {
            switchTo = new CoachSwitchFragment();
        } else if (id == R.id.previous_workout) {

        } else if (id == R.id.create_workout) {
            switchTo = new CreateWorkoutFragment();
        } else if (id == R.id.share_workout) {

        } else if(id == R.id.todays_workout){
            switchTo = new TodayWorkoutFragment();
        }else if(id == R.id.sign_out){
            onLogout();
        }

        if(switchTo != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, switchTo);
            int nEntries = getSupportFragmentManager().getBackStackEntryCount();
            for(int i =0;i<nEntries;i++){
                getSupportFragmentManager().popBackStackImmediate();
            }
            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isExpired(AuthData authData) {
        return (System.currentTimeMillis() / 1000) >= authData.getExpires();
    }


    class MyAuthResultHandler implements Firebase.AuthResultHandler{

        @Override
        public void onAuthenticated(AuthData authData) {
            switchToPasswordFragment(Constants.FIREBASE_URL + "users/" + authData.getUid());
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            showLoginError(firebaseError.getMessage());
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(Constants.TAG, "Connection failed");

    }


    @Override
    public void onLogin(String email, String password) {
        //TODO: Log user in with username & password
        mFirebase.authWithPassword(email, password, new MyAuthResultHandler());

    }

    @Override
    public void onRosefireLogin(String email, String password) {
        RosefireAuth roseAuth = new RosefireAuth(mFirebase, getString(R.string.rose_fire_token));
        roseAuth.authWithRoseHulman(email, password, new MyAuthResultHandler());

    }

    @Override
    public void onTwitterLogin() {
        startActivityForResult(new Intent(this, TwitterOAuthActivity.class), RC_TWITTER_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Map<String, String> options = new HashMap<String, String>();
        if(requestCode == RC_TWITTER_LOGIN){
            options.put("oauth_token", data.getStringExtra("oauth_token"));
            options.put("oauth_token_secret", data.getStringExtra("oauth_token_secret"));
            options.put("user_id", data.getStringExtra("user_id"));
            onTwitterLogin("twitter", options);
        }
    }

    private void onTwitterLogin(final String provider, Map<String, String> options) {
        Log.d(Constants.TAG, "onTwitterLoginWithToken: ");
        mFirebase.authWithOAuthToken(provider, options, new MyAuthResultHandler());
    }

    @Override
    public void onLogout() {        
        //DONE: Log the user out.
        mFirebase.unauth();
        switchToLoginFragment();
    }

    // MARK: Provided Helper Methods
    private void switchToLoginFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, new LoginFragment(), "Login");
        ft.commit();
    }

    private void switchToPasswordFragment(String repoUrl) {
        USER_AUTH =repoUrl;
        Log.d(Constants.TAG, "switchToPasswordFragment: " + repoUrl);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment todays_workout_fragment = new TodayWorkoutFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FIREBASE, repoUrl);
        todays_workout_fragment.setArguments(args);
        ft.replace(R.id.fragment, new TodayWorkoutFragment(), "Todays Workout");
        ft.commit();
    }

    private void showLoginError(String message) {
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag("Login");
        loginFragment.onLoginError(message);
    }

}
