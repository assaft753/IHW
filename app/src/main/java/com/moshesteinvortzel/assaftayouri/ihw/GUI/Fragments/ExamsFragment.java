package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

public class ExamsFragment extends android.support.v4.app.Fragment implements RefreshDataSetListener
{
    private final int ITEM = 2;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private UngradedExamFragment ungradedExamFragment;
    private GraderExamFragment graderExamFragment;
    private int currentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ungradedExamFragment = new UngradedExamFragment();
        graderExamFragment = new GraderExamFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_exams, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.examTabs);
        viewPager = (ViewPager) view.findViewById(R.id.examViewPager);
        viewPager.setAdapter(new GradePagesAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        //TextView textView = (TextView) view.findViewById(R.id.pickClass);

        //textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //view.findViewById(R.id.ovalgrade).setBackground(new Oval("ss", 6));
        //((TextView) view.findViewById(R.id.ovalgrade)).setTextColor(Color.parseColor("#7B1FA2"));
        //view.findViewById(R.id.container).setBackground(new Border("7B1FA2", 20));
        /*((TextView) view.findViewById(R.id.ovalgrade)).setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                GradeDialog gradeDialog = new GradeDialog();
                gradeDialog.show(getFragmentManager(), "ss");
                return true;
            }
        });*/


        return view;
    }

    @Override
    public void RefreshDataSet()
    {
        switch (currentFragment)
        {
            case 0:
                ungradedExamFragment.RefreshDataSet();
            case 1:
                graderExamFragment.RefreshDataSet();
        }
    }


    class GradePagesAdapter extends FragmentPagerAdapter
    {
        public GradePagesAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    currentFragment = 0;
                    return ungradedExamFragment;
                case 1:
                    currentFragment = 1;
                    return graderExamFragment;
            }
            return null;
        }

        @Override
        public int getCount()
        {
            return ITEM;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0:
                    return "UnGraded";
                case 1:
                    return "Graded";
            }
            return null;
        }
    }

}