package com.moshesteinvortzel.assaftayouri.ihw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private int CurrentFragment;
    private String[] toolbarTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbarTitles=getResources().getStringArray(R.array.nav_titles);
        CurrentFragment=0;
        SetCurrentFragment();
    }

    private void SetCurrentFragment()
    {
        SetItemFocus();
        SetToolBarTitle();
        Fragment fragment;
        FragmentTransaction fragmentTransaction;
        switch (CurrentFragment)
        {
            case 0:
                fragment = new HomeWorkFragment();
                break;
            case 1:
                fragment = new ClassesFragment();
                break;
            case 2:
                fragment = new ExamsFragment();
                break;
            case 3:
                fragment = new CalenderFragment();
                break;
            default:
                fragment = new HomeWorkFragment();
        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();

    }

    private void SetToolBarTitle()
    {
        ((TextView)findViewById(R.id.toolbar_title)).setText(this.toolbarTitles[CurrentFragment]);
    }

    private void SetItemFocus()
    {
        if(navigationView.getMenu().getItem(CurrentFragment).isChecked()==false)
        {
            navigationView.getMenu().getItem(CurrentFragment).setChecked(true);
        }
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        if (id == R.id.addToolBarButton)
        {
            System.out.println("Pressed");
            //Make Intent To Add Page
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int fragmentNumber=0;
        switch (item.getItemId())
        {
            case R.id.navHomeWork:
                fragmentNumber=0;
                break;
            case R.id.navClasses:
                fragmentNumber=1;
                break;
            case R.id.navExams:
                fragmentNumber=2;
                break;
            case R.id.navCalander:
                fragmentNumber=3;
                break;
        }

        if(fragmentNumber!=CurrentFragment)
        {
            CurrentFragment=fragmentNumber;
            SetCurrentFragment();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LogOut(View view)
    {
    }
}
