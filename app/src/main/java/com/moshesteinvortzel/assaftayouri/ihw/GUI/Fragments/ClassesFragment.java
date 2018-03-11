package com.moshesteinvortzel.assaftayouri.ihw.GUI.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moshesteinvortzel.assaftayouri.ihw.GUI.Adapters.ClassesAdapter;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.ClassItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.GUI.SwipeHelpers.RecyclerHourItemHelper;
import com.moshesteinvortzel.assaftayouri.ihw.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClassesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassesFragment extends android.support.v4.app.Fragment implements RecyclerHourItemHelper.RecyclerItemTouchHelperListener
{
    private RecyclerView recyclerView;
    private ClassesAdapter classesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_classes, container, false);
        this.recyclerView=view.findViewById(R.id.classesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        classesAdapter=new ClassesAdapter();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ClassItemHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        this.recyclerView.setAdapter(classesAdapter);
        classesAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {

    }
}
