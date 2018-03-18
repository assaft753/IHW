package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.CalendarItemAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.CompletedHWAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.UncompletedHWAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.CompleteTwoSidesItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.UnCompleteTwoSidesItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.HomeWork;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnHWDialogListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnLongHWItemListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.SwipeHelperListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;
//import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.RecyclerCompleteHWItemHelper;
//import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.RecyclerHourItemHelper;

import java.util.ArrayList;

public class CompletedHWFragment extends android.support.v4.app.Fragment implements SwipeHelperListener, RefreshDataSetListener, OnLongHWItemListener
{
    private RecyclerView completedRecyclerView;
    private ArrayList<HomeWork> completedList;
    private CompletedHWAdapter completedHWAdapter;
    private OnHWDialogListener onHWDialogListener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    public void setOnHWDialogListener(OnHWDialogListener onHWDialogListener)
    {
        this.onHWDialogListener = onHWDialogListener;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        if (isVisibleToUser && completedHWAdapter != null)
        {
            completedHWAdapter.notifyDataSetChanged();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_completed_hw, container, false);
        completedRecyclerView = view.findViewById(R.id.completedHWList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        completedRecyclerView.setLayoutManager(mLayoutManager);
        completedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        completedList = User.Student.getCompletedHW();
        completedHWAdapter = new CompletedHWAdapter(completedList, this);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new UnCompleteTwoSidesItemHelper<CompletedHWAdapter.CompletedHWViewHolder>(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(completedRecyclerView);
        completedRecyclerView.setAdapter(completedHWAdapter);
        return view;

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        HomeWork homeWork;
        if (direction == ItemTouchHelper.RIGHT)
        {
            homeWork = User.Student.RemoveHW(position, true, getContext());
        }
        else
        {
            User.Student.FromCompletedToUncompleted(position, getContext());
        }
        completedHWAdapter.notifyItemRemoved(position);

    }

    @Override
    public void RefreshDataSet()
    {
        if (completedHWAdapter != null)
        {
            completedHWAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnLongHWItem(String index)
    {
        onHWDialogListener.OnHWDialog(index);
    }
}
