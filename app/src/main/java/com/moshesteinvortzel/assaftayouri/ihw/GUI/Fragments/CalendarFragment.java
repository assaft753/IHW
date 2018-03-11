package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.CalendarItemAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.Calendar;

public class CalendarFragment extends android.support.v4.app.Fragment
{
    RecyclerView.Adapter calanderItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        CompactCalendarView calendarView=view.findViewById(R.id.calendarView);
        RecyclerView recyclerView=view.findViewById(R.id.calendarItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        calanderItemAdapter=new CalendarItemAdapter(getContext());
        recyclerView.setAdapter(calanderItemAdapter);
        calanderItemAdapter.notifyDataSetChanged();
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        return view;
    }

}
