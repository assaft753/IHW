package com.moshesteinvortzel.assaftayouri.ihw.GUI.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs.AddExamDialog;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs.AddHWDialog;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.AddHWFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.CalendarFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.ClassesFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.ExamsFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.HomeWorkFragment;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Student;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnHWDialogListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.ShowDialogExamListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CourseDay;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.security.Permission;
import java.security.Permissions;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RefreshDataSetListener, ShowDialogExamListener, OnHWDialogListener
{
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private int CurrentFragment;
    private String[] toolbarTitles;
    private ActionBarDrawerToggle toggle;
    private HomeWorkFragment homeWorkFragment;
    private ClassesFragment classesFragment;
    private CalendarFragment calendarFragment;
    private ExamsFragment examsFragment;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeWorkFragment = new HomeWorkFragment();
        homeWorkFragment.setOnHWDialogListener(this);
        classesFragment = new ClassesFragment();
        calendarFragment = new CalendarFragment();
        examsFragment = new ExamsFragment();
        examsFragment.setDialogExamListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR}, 0);
        }
        User.Student = new Student(2, "assaf@gmail.com", "122345", "Assaf Tayouri");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, 30);
        Course course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course, getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navView);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.userEmail)).setText(User.Student.getEmail());
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.userFullName)).setText(User.Student.getUserName());
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
                fragment = homeWorkFragment;
                break;
            case 1:
                fragment = classesFragment;
                break;
            case 2:
                fragment = examsFragment;
                break;
            case 3:
                fragment = calendarFragment;
                break;
            default:
                fragment = homeWorkFragment;
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
        AddDialog newFragment = new AddDialog();
        newFragment.setRefreshDataSetListener(this);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    @Override
    public void ShowDialogExam(int pos)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddExamDialog examDialog = new AddExamDialog();
        Bundle bundle = new Bundle();
        bundle.putString("opt", String.valueOf(pos));
        examDialog.setArguments(bundle);
        examDialog.setRefreshDataSetListener(this);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, examDialog).addToBackStack(null).commit();
    }


    @Override
    public void RefreshDataSet()
    {
        ((RefreshDataSetListener) fragment).RefreshDataSet();
    }

    @Override
    public void OnHWDialog(String index)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddHWDialog addHWDialog = new AddHWDialog();
        Bundle bundle = new Bundle();
        bundle.putString("opt", String.valueOf(index));
        addHWDialog.setArguments(bundle);
        addHWDialog.setRefreshDataSetListener(this);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, addHWDialog).addToBackStack(null).commit();
    }
}
