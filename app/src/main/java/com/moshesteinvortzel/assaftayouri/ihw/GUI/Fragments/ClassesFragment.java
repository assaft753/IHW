package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.ClassesAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs.AddClassDialog;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.OneSideItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.Course;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Core.User;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.OnLongClassItemListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.RefreshDataSetListener;
import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.SwipeHelperListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

import java.util.ArrayList;


public class ClassesFragment extends android.support.v4.app.Fragment implements SwipeHelperListener, RefreshDataSetListener,OnLongClassItemListener
{
    private RecyclerView recyclerView;
    private ClassesAdapter classesAdapter;
    private ArrayList<Course> courses;
    private AddClassDialog addClassDialog;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        courses = (ArrayList<Course>) User.Student.getCourses();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_classes, container, false);
        this.recyclerView = view.findViewById(R.id.classesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        classesAdapter = new ClassesAdapter(courses,this);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new OneSideItemHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        this.recyclerView.setAdapter(classesAdapter);
        classesAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        User.Student.RemoveClassElements(position,getContext());
        classesAdapter.removeItem(position);
    }

    @Override
    public void RefreshDataSet()
    {
        this.classesAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnLongClassItem(int pos)
    {
        showDialog(pos);
    }

    public void showDialog(int pos)
    {
        FragmentManager fragmentManager = getFragmentManager();
        addClassDialog = new AddClassDialog();
        Bundle bundle=new Bundle();
        bundle.putString("opt",String.valueOf(pos));
        addClassDialog.setArguments(bundle);
        addClassDialog.setRefreshDataSetListener(this);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, addClassDialog).addToBackStack(null).commit();
    }
}
