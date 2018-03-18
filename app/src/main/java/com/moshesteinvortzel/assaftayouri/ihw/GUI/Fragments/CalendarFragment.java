package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.CalendarAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Secondary.CalendarHelper;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends android.support.v4.app.Fragment implements RefreshDataSetListener
{
    private final String DATE_PATTERN = "MMMM yyyy";
    private CalendarAdapter calendarAdapter;
    private CompactCalendarView calendarView;
    private RecyclerView calendarItemList;
    private TextView dateText;
    private Date currentDate;
    private ArrayList<CalendarHelper> calendarHelperList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        calendarItemList = view.findViewById(R.id.calendarItemList);
        dateText = view.findViewById(R.id.dateText);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        calendarItemList.setLayoutManager(mLayoutManager);
        calendarItemList.setItemAnimator(new DefaultItemAnimator());
        calendarItemList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        calendarAdapter = new CalendarAdapter();
        calendarItemList.setAdapter(calendarAdapter);
        RefreshCalendarEvents();
        calendarAdapter.notifyDataSetChanged();
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        if (currentDate == null)
        {
            currentDate = calendarView.getFirstDayOfCurrentMonth();
        }
        else
        {
            calendarView.setCurrentDate(currentDate);
        }
        RefreshDataSet();

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener()
        {
            @Override
            public void onDayClick(Date dateClicked)
            {
                currentDate = dateClicked;
                RefreshDataSet();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth)
            {
                currentDate = firstDayOfNewMonth;
                RefreshDataSet();
            }
        });

        return view;
    }

    private void RefreshCalendarEvents()
    {
        calendarView.removeAllEvents();
        ArrayList<Event> events = User.Student.GetEvents();
        System.out.println(events.size());
        calendarView.addEvents(events);
    }

    @Override
    public void RefreshDataSet()
    {
        UpdateDateText(currentDate);
        List<CalendarHelper> calendarHelpers = User.Student.GetListOFTasksInDate(currentDate);
        calendarAdapter.setCalendarHelpers(calendarHelpers);
        RefreshCalendarEvents();
    }


    private void UpdateDateText(Date date)
    {
        SimpleDateFormat ft = new SimpleDateFormat(DATE_PATTERN);
        dateText.setText(ft.format(date));
    }
}
