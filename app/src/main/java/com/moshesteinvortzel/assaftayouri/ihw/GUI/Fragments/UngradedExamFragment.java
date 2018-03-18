package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.UngradedExamAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs.GradeDialog;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.CompleteTwoSidesItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Exam;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnLongUngradedItemListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.ShowDialogExamListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.SwipeHelperListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.ArrayList;

/**
 * Created by assaftayouri on 08/03/2018.
 */

public class UngradedExamFragment extends Fragment implements RefreshDataSetListener, SwipeHelperListener, OnLongUngradedItemListener, DialogInterface.OnClickListener, DialogInterface.OnCancelListener
{
    private RecyclerView ungradedRecyclerView;
    private ArrayList<Exam> ungradedList;
    private UngradedExamAdapter ungradedExamAdapter;
    private ShowDialogExamListener dialogExamListener;
    private Exam aboutToGraded;
    private int getAboutToGradedIndex;
    private GradeDialog gradeDialog;

    public void setDialogExamListener(ShowDialogExamListener dialogExamListener)
    {
        this.dialogExamListener = dialogExamListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.ungraded_exam, container, false);
        ungradedRecyclerView = view.findViewById(R.id.ungradedExamList);
        ungradedList = User.Student.getUngradedExams();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        ungradedRecyclerView.setLayoutManager(mLayoutManager);
        ungradedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ungradedExamAdapter = new UngradedExamAdapter(ungradedList, this);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new CompleteTwoSidesItemHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
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
    public void setMenuVisibility(boolean menuVisible)
    {
        if (ungradedExamAdapter != null && menuVisible)
        {
            ungradedExamAdapter.notifyDataSetChanged();
        }
        super.setMenuVisibility(menuVisible);
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
            gradeDialog = new GradeDialog();
            getAboutToGradedIndex = position;
            aboutToGraded = User.Student.AddGradedExamPartly(position);
            gradeDialog.setOnClickListener(this);
            gradeDialog.setOnCancelListener(this);
            ungradedExamAdapter.notifyItemRemoved(position);
            gradeDialog.show(getFragmentManager(), "Number Picker");

        }
        ungradedExamAdapter.notifyItemRemoved(position);

    }

    @Override
    public void OnLongUngradedItem(int pos)
    {
        dialogExamListener.ShowDialogExam(pos);
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i)
    {
        if (i == - 1)
        {
            User.Student.AddGradedExamFinish(aboutToGraded, gradeDialog.getPicker().getValue(), getContext());
        }
        else
        {
            User.Student.CanceledGradedExam(aboutToGraded, getAboutToGradedIndex);
            ungradedExamAdapter.notifyItemInserted(getAboutToGradedIndex);
        }
    }


    @Override
    public void onCancel(DialogInterface dialogInterface)
    {
        User.Student.CanceledGradedExam(aboutToGraded, getAboutToGradedIndex);
        ungradedExamAdapter.notifyItemInserted(getAboutToGradedIndex);
    }
}
