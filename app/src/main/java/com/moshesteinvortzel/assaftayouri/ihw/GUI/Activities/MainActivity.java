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
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs.AddExamDialog;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.CalendarFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.ClassesFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.ExamsFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.HomeWorkFragment;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Student;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.ShowDialogExamListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CourseDay;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RefreshDataSetListener, ShowDialogExamListener
{
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private int CurrentFragment;
    private String[] toolbarTitles;
    private ActionBarDrawerToggle toggle;
    //private AddDialog newFragment;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User.Student = new Student(2, "assaf@gmail.com", "122345", "Assaf Tayouri");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, 18);
        Course course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
         course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
         course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
         course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
         course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
         course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);
        course = new Course("Algebra", (float) 2.6, calendar, calendar1, 0xff2196f3, new ArrayList<CourseDay>());
        User.Student.AddClass(course);


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
                ((ExamsFragment) fragment).setDialogExamListener(this);
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
}
