package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.CalendarItemAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.HomeWork;
import com.moshesteinvortzel.assaftayouri.ihw.R;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.RecyclerCompleteHWItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.RecyclerHourItemHelper;

import java.util.ArrayList;

public class CompletedHWFragment extends android.support.v4.app.Fragment implements RecyclerHourItemHelper.RecyclerItemTouchHelperListener
{
    public ArrayList<HomeWork> completedList=new ArrayList<>();
    private RecyclerView recyclerView;
    //private CompletedAdapter completedAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_completed_hw, container, false);
        recyclerView=view.findViewById(R.id.completedHWList);
        InitArray();
        //completedAdapter=new CompletedAdapter(completedList,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerCompleteHWItemHelper(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        //recyclerView.setAdapter(completedAdapter);
        //completedAdapter.notifyDataSetChanged();
        return view;
    }

    private void InitArray()
    {
       /* completedList.add(new HomeWork("Algebra","Wed, Aug 21 - 9:30 AM",2,"0277BD"));
        completedList.add(new HomeWork("Computer Science","Fri, Feb 21 - 9:30 AM",2,"455A64"));
        completedList.add(new HomeWork("quiz 1","Sat, Dec 21 - 9:30 AM",2,"4E342E"));
        completedList.add(new HomeWork("Test","Wed, Oct 21 - 9:30 AM",2,"FF9E80"));
        completedList.add(new HomeWork("Algebra","Thu, Jan 21 - 9:30 AM",2,"FF6F00"));
        completedList.add(new HomeWork("Algebra","Sun, Jul 21 - 9:30 AM",2,"00E676"));
        completedList.add(new HomeWork("Algebra","Wed, Jun 21 - 9:30 AM",2,"0277BD"));
        completedList.add(new HomeWork("Algebra","Wed, Sep 21 - 9:30 AM",2,"E43F3F"));
        completedList.add(new HomeWork("Algebra","Wed, Mar 21 - 9:30 AM",2,"f54d83"));
        completedList.add(new HomeWork("Algebra","Wed, May 21 - 9:30 AM",2,"0D47A1"));*/

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        //this.completedAdapter.removeItem(position);
    }
}

