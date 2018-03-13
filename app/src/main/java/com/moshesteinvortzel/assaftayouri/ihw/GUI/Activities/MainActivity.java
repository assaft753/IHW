package com.moshesteinvortzel.assaftayouri.ihw.GUI.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.view.Window;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs.AddDialog;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.CalendarFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.ClassesFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.ExamsFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.HomeWorkFragment;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Student;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.security.PrivateKey;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RefreshDataSetListener
{
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private int CurrentFragment;
    private String[] toolbarTitles;
    private ActionBarDrawerToggle toggle;
    private AddDialog newFragment;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User.Student = new Student(2, "assaf@gmail.com", "122345", "Assaf Tayouri");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        toolbarTitles = getResources().getStringArray(R.array.nav_titles);
        CurrentFragment = 0;
        SetCurrentFragment();
    }

    private void SetCurrentFragment()
    {
        SetItemFocus();
        SetToolBarTitle();
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
                fragment = new CalendarFragment();
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
        ((TextView) findViewById(R.id.toolbarTitleText)).setText(this.toolbarTitles[CurrentFragment]);
    }

    private void SetItemFocus()
    {
        if (navigationView.getMenu().getItem(CurrentFragment).isChecked() == false)
        {
            navigationView.getMenu().getItem(CurrentFragment).setChecked(true);
        }
    }

    @Override
    public void onBackPressed()
    {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            showDialog();
            //Make Intent To Add Page
            //return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int fragmentNumber = 0;
        switch (item.getItemId())
        {
            case R.id.navHomeWork:
                fragmentNumber = 0;
                break;
            case R.id.navClasses:
                fragmentNumber = 1;
                break;
            case R.id.navExams:
                fragmentNumber = 2;
                break;
            case R.id.navCalander:
                fragmentNumber = 3;
                break;
        }

        if (fragmentNumber != CurrentFragment)
        {
            CurrentFragment = fragmentNumber;
            SetCurrentFragment();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showDialog()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        newFragment = new AddDialog();
        newFragment.setRefreshDataSetListener(this);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }


    @Override
    public void RefreshDataSet()
    {
        ((RefreshDataSetListener) fragment).RefreshDataSet();
    }
}
