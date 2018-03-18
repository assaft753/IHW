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
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.ShowDialogExamListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

public class ExamsFragment extends android.support.v4.app.Fragment implements RefreshDataSetListener, ShowDialogExamListener
{
    private final int ITEM = 2;
    private ShowDialogExamListener dialogExamListener;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private UngradedExamFragment ungradedExamFragment;
    private GradedExamFragment gradedExamFragment;

    public void setDialogExamListener(ShowDialogExamListener dialogExamListener)
    {
        this.dialogExamListener = dialogExamListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ungradedExamFragment = new UngradedExamFragment();
        ungradedExamFragment.setDialogExamListener(this);
        gradedExamFragment = new GradedExamFragment();
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
        return view;
    }

    @Override
    public void RefreshDataSet()
    {
        ungradedExamFragment.RefreshDataSet();
        gradedExamFragment.RefreshDataSet();
    }

    @Override
    public void ShowDialogExam(int pos)
    {
        this.dialogExamListener.ShowDialogExam(pos);
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
                    return ungradedExamFragment;
                case 1:
                    return gradedExamFragment;
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
                    return getResources().getString(R.string.unGraded);
                case 1:
                    return getResources().getString(R.string.Graded);
            }
            return null;
        }
    }

}
