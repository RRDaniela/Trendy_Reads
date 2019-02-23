package com.example.danny.trendy_reads.activities.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.danny.trendy_reads.R;
import com.example.danny.trendy_reads.activities.Activities.Chat.ChatActivity;
import com.example.danny.trendy_reads.activities.Activities.Settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    private Toolbar mToolbar;
    FirebaseUser currentUser;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MessagesIntent = new Intent(Home.this, ChatActivity.class );
                startActivity(MessagesIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateNavHeader();

        //Tabs


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.paginaprincipal) {

            getSupportActionBar().setTitle("Página Principal");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new com.example.danny.trendy_reads.activities.Fragments.Home()).commit();

        } else if (id == R.id.grupos) {

            getSupportActionBar().setTitle("Grupos");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new com.example.danny.trendy_reads.activities.Fragments.Grupos()).commit();

        } else if (id == R.id.rotalibro) {

            getSupportActionBar().setTitle("Rotalibro");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new com.example.danny.trendy_reads.activities.Fragments.Rotalibro()).commit();

        } else if (id == R.id.perfil) {
            getSupportActionBar().setTitle("Perfil");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new com.example.danny.trendy_reads.activities.Fragments.Profile()).commit();

        } else if (id == R.id.ajustes) {
            getSupportActionBar().setTitle("Ajustes");
            //getSupportFragmentManager().beginTransaction().replace(R.id.container, new com.example.danny.trendy_reads.activities.Fragments.Settings()).commit();
            Intent Settings = new Intent(Home.this, SettingsActivity.class );
            startActivity(Settings);
        }

        else if (id == R.id.signout){
            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_username);
        TextView navUsermail = headerView.findViewById(R.id.nav_usermail);
        ImageView navUserphoto = headerView.findViewById(R.id.nav_user_photo);

        navUsermail.setText(currentUser.getEmail());
        navUsername.setText(currentUser.getDisplayName());

        //Cargar imagen del usuario

        Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserphoto);


    }
}
