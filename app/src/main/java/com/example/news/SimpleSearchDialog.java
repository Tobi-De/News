package com.example.news;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SimpleSearchDialog extends AppCompatDialogFragment {

    private EditText topic;
    private RadioButton newest;
    private Spinner lang;
    private SimpleDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.simple_search, null);

        builder.setView(view)
                .setTitle(R.string.topic)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String topicS = topic.getText().toString();
                Boolean newestB = newest.isChecked();
                String langL = lang.getSelectedItem().toString();
                listener.makeSearch(topicS, newestB, langL);
            }
        });

        topic = view.findViewById(R.id.topic_edit);
        newest = view.findViewById(R.id.newest);
        lang = view.findViewById(R.id.lang);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SimpleDialogListener) context;
        } catch (ClassCastException e) {
           throw new ClassCastException(context.toString() + "must implement SimpleSearchDialog");
        }
    }

    public interface SimpleDialogListener{
        void makeSearch(String topic, Boolean newest, String lang);
    }
}
