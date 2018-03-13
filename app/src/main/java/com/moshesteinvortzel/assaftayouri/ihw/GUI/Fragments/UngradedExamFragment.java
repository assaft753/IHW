package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.UngradedExamAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.CompleteItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.DeleteItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Border;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Views.Oval;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Exam;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Student;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.SwipeHelperListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.ArrayList;

/**
 * Created by assaftayouri on 08/03/2018.
 */

public class UngradedExamFragment extends Fragment implements RefreshDataSetListener, SwipeHelperListener
{
    private RecyclerView ungradedRecyclerView;
    private ArrayList<Exam> ungradedList;
    private UngradedExamAdapter ungradedExamAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.ungraded_exam, container, false);
        ungradedRecyclerView = view.findViewById(R.id.ungradedExamList);
        ungradedList = User.Student.getUngradedExams();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        ungradedRecyclerView.setLayoutManager(mLayoutManager);
        ungradedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ungradedExamAdapter = new UngradedExamAdapter(ungradedList);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new CompleteItemHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(ungradedRecyclerView);
        ungradedRecyclerView.setAdapter(ungradedExamAdapter);

        return view;
    }

    @Override
    public void RefreshDataSet()
    {
        ungradedExamAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        if (direction == ItemTouchHelper.LEFT)
        {

            User.Student.RemoveExam(position, false, getContext());
        }
        else
        {
            User.Student.AddGradedExam(position, getContext());

        }
        ungradedExamAdapter.notifyItemRemoved(position);
    }
}
