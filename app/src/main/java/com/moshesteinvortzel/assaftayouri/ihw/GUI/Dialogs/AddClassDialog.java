package com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.AddClassFirstFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.AddClassSecondFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.AddFragment;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments.ClassesFragment;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.DismissListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.FirstAddClassListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

/**
 * Created by assaftayouri on 11/03/2018.
 */

public class AddClassDialog extends DialogFragment implements FirstAddClassListener, DismissListener
{
    RefreshDataSetListener refreshDataSetListener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    public void setRefreshDataSetListener(RefreshDataSetListener refreshDataSetListener)
    {
        this.refreshDataSetListener = refreshDataSetListener;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog d = getDialog();
        if (d != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_add, container, false);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        AddClassFirstFragment addClassFirstFragment = new AddClassFirstFragment();
        addClassFirstFragment.setListener(this);
        addClassFirstFragment.setArguments(getArguments());
        fragmentTransaction.replace(R.id.addDialogFrame, addClassFirstFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
        return view;
    }


    @Override
    public void MoveToSecondAddClass(String courseName, float coursePoints, long startDate, long endDate, int color, boolean isExists, int index)
    {
        Bundle bundle = new Bundle();
        bundle.putString("courseName", courseName);
        bundle.putFloat("coursePoints", coursePoints);
        bundle.putLong("startDate", startDate);
        bundle.putLong("endDate", endDate);
        bundle.putInt("color", color);
        bundle.putBoolean("isExists", isExists);
        bundle.putInt("index", index);
        AddClassSecondFragment addClassSecondFragment = new AddClassSecondFragment();
        addClassSecondFragment.setDialogDismissCallback(this);
        addClassSecondFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.addDialogFrame, addClassSecondFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void Dismiss()
    {
        refreshDataSetListener.RefreshDataSet();
        this.dismiss();
    }
}
