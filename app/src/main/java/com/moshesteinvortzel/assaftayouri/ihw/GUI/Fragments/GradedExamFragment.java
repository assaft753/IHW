package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

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

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.GradedExamAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs.GradeDialog;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.TwoSidesItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Exam;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnLongGradedItemListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.ShowDialogExamListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.SwipeHelperListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.ArrayList;

/**
 * Created by assaftayouri on 08/03/2018.
 */

public class GradedExamFragment extends Fragment implements RefreshDataSetListener, OnLongGradedItemListener, SwipeHelperListener
{

    private RecyclerView gradedRecyclerView;
    private ArrayList<Exam> gradedList;
    private GradedExamAdapter gradedExamAdapter;
    private ShowDialogExamListener dialogExamListener;
    private GradeDialog gradeDialog;
    private TextView pointsText;
    private TextView avgText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.graded_exam, container, false);
        gradedRecyclerView = view.findViewById(R.id.gradedExamList);
        pointsText = view.findViewById(R.id.gradedExamPointsText);
        avgText = view.findViewById(R.id.gradedExamAvgText);
        gradedList = User.Student.getGradedExams();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        gradedRecyclerView.setLayoutManager(mLayoutManager);
        gradedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        gradedExamAdapter = new GradedExamAdapter(gradedList, this, null);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new TwoSidesItemHelper<GradedExamAdapter.GradedExamViewHolder>(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(gradedRecyclerView);
        gradedRecyclerView.setAdapter(gradedExamAdapter);
        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible)
    {
        if (gradedExamAdapter != null && menuVisible)
        {
            RefreshDataSet();
        }
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void RefreshDataSet()
    {
        gradedExamAdapter.notifyDataSetChanged();
        SetAverageTexts();
    }

    private void SetAverageTexts()
    {
        float[] result = User.Student.CalculateExamsAvg();
        System.out.println("ttttt");
        pointsText.setText(String.valueOf(result[1]));
        avgText.setText(String.valueOf(result[0]));
    }

    /*@Override
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
            ungradedExamAdapter.notifyItemRemoved(position);
            gradeDialog.show(getFragmentManager(), "Number Picker");


            //User.Student.AddGradedExam(position, getContext());

        }
        //ungradedExamAdapter.notifyItemRemoved(position);
        System.out.println("here");

    }*/

    @Override
    public void OnLongGradedItem(int pos)
    {
        dialogExamListener.ShowDialogExam(pos);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        if (direction == ItemTouchHelper.RIGHT)
        {

            User.Student.RemoveExam(position, true, getContext());
        }
        else
        {
            User.Student.FromGradedToUngraded(position, getContext());
        }
        gradedExamAdapter.notifyItemRemoved(position);
        SetAverageTexts();

    }


    /*@Override
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
        //ungradedExamAdapter.notifyItemInserted(aboutToGraded);
    }*/
}

