package com.example.assignment06;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Assignment 6
// Group 7: Jaiden Daya, Nhu Nguyen
public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener,
CreateUserFragment.CreateUserFragmentListener, ProfileFragment.ProfileFragmentListener, EditUserFragment.EditUserFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction()
                        .add(R.id.rootView, new MainFragment())
                        .commit();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void gotoCreateUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new CreateUserFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToProfile(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ProfileFragment.newInstance(user), "profile-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoEditUser(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, EditUserFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoProfile(User user) {
        ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("profile-fragment");
        if(fragment != null){
            fragment.setUser(user);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelEdit() {
        getSupportFragmentManager().popBackStack();
    }

}