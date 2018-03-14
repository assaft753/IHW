package com.moshesteinvortzel.assaftayouri.ihw.GUI.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.ihw.LOGIC.Interfaces.AddExamDialogListener;
import com.moshesteinvortzel.assaftayouri.ihw.R;

/**
 * Created by assaftayouri on 14/03/2018.
 */

public class GradeDialog extends DialogFragment
{
    private String[] strs;
    private NumberPicker picker;
    private TextView titleText;
    private View view;
    private DialogInterface.OnClickListener onClickListener;

    public NumberPicker getPicker()
    {
        return picker;
    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        System.out.println("dialog created");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_picker, null);
        this.view = view;
        picker = view.findViewById(R.id.itemPicker);
        titleText = view.findViewById(R.id.pickerText);
        SetPickerOpt();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setPositiveButton("OK", this.onClickListener).setNegativeButton("Cancel", this.onClickListener);
        return builder.create();
    }


    private void SetPickerOpt()
    {
        picker.setMinValue(0);
        picker.setMaxValue(100);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        picker.setWrapSelectorWheel(true);
    }
}
