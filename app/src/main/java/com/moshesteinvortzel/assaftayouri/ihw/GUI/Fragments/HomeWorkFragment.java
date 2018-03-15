package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

public class HomeWorkFragment extends Fragment implements RefreshDataSetListener
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private UncompletedHWFragment uncompletedHWFragment;
    private CompletedHWFragment completedHWFragment;
    private final int ITEMS = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home_work, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.hwTabs);
        viewPager = (ViewPager) view.findViewById(R.id.hwViewPager);
        uncompletedHWFragment = new UncompletedHWFragment();
        completedHWFragment = new CompletedHWFragment();
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
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
        uncompletedHWFragment.RefreshDataSet();
    }

    class MyAdapter extends FragmentPagerAdapter
    {
        public MyAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return uncompletedHWFragment;
                case 1:
                    return completedHWFragment;
            }
            return null;
        }

        @Override
        public int getCount()
        {
            return ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0:
                    return "UnCompleted";
                case 1:
                    return "Completed";
            }
            return null;
        }
    }
}


