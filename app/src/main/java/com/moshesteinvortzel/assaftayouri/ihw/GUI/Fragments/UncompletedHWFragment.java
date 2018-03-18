package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.UncompletedHWAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.CompleteTwoSidesItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.HomeWork;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnHWDialogListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnLongHWItemListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.SwipeHelperListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.ArrayList;
import java.util.Arrays;

public class UncompletedHWFragment extends android.support.v4.app.Fragment implements SwipeHelperListener, RefreshDataSetListener, OnLongHWItemListener
{
    private RecyclerView uncompletedRecyclerView;
    private ArrayList<HomeWork> uncompletedList;
    private UncompletedHWAdapter uncompletedHWAdapter;
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
        System.out.println(isVisibleToUser);
        if (isVisibleToUser && uncompletedHWAdapter != null)
        {
            uncompletedHWAdapter.notifyDataSetChanged();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_uncompleted_hw, container, false);
        uncompletedRecyclerView = view.findViewById(R.id.uncompletedHWList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        uncompletedRecyclerView.setLayoutManager(mLayoutManager);
        uncompletedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        uncompletedList = User.Student.getUncompletedHW();
        uncompletedHWAdapter = new UncompletedHWAdapter(uncompletedList, this);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new CompleteTwoSidesItemHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(uncompletedRecyclerView);
        uncompletedRecyclerView.setAdapter(uncompletedHWAdapter);
        return view;

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        HomeWork homeWork;
        if (direction == ItemTouchHelper.LEFT)
        {
            homeWork = User.Student.RemoveHW(position, false, getContext());
        }
        else
        {
            User.Student.AddCompletedHW(position, getContext());
        }
        uncompletedHWAdapter.notifyItemRemoved(position);

    }

    @Override
    public void RefreshDataSet()
    {
        if (uncompletedHWAdapter != null)
        {
            uncompletedHWAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnLongHWItem(String index)
    {
        onHWDialogListener.OnHWDialog(index);
    }
}
