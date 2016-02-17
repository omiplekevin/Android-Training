package com.example.omiplekevin.ndudemoapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by OMIPLEKEVIN on 13/12/2015.
 *
 */
public class AddItemDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    EditText newItemEditText;

    public interface NewItemAddedListener{
        void onFinishedAdding(String newItem);
    }

    public AddItemDialogFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_item, container);
        newItemEditText = (EditText) view.findViewById(R.id.newItemEditText);
        newItemEditText.setOnEditorActionListener(this);
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            NewItemAddedListener actionListener = (NewItemAddedListener) getActivity();
            String item = newItemEditText.getText().toString().trim();
            actionListener.onFinishedAdding(item);
            this.dismiss();
            return true;
        }
        return false;
    }


}
