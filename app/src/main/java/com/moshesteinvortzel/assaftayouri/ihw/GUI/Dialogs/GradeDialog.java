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
    private final int MIN_VALUE = 0;
    private final int MAX_VALUE = 100;
    private String[] strs;
    private NumberPicker picker;
    private View view;
    private DialogInterface.OnClickListener onClickListener;
    private DialogInterface.OnCancelListener onCancelListener;

    public NumberPicker getPicker()
    {
        return picker;
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener)
    {
        this.onCancelListener = onCancelListener;
    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_picker, null);
        this.view = view;
        picker = view.findViewById(R.id.itemPicker);
        SetPickerOpt();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setPositiveButton(R.string.ok, onClickListener).setNegativeButton(R.string.cancel, onClickListener);
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        onCancelListener.onCancel(dialog);
        super.onCancel(dialog);
    }

    private void SetPickerOpt()
    {
        picker.setMinValue(MIN_VALUE);
        picker.setMaxValue(MAX_VALUE);
        picker.setValue(picker.getMaxValue());
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        picker.setWrapSelectorWheel(true);
    }
}
