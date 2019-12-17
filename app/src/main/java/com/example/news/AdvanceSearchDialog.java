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

import com.example.news.R;

public class AdvanceSearchDialog extends AppCompatDialogFragment {

    private EditText topic;
    private RadioButton bySource;
    private Spinner sources;
    private Spinner country;
    private Spinner category;
    private AdvanceDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.advance_search, null);

        builder.setView(view)
                .setTitle(R.string.healdines)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String topicS = topic.getText().toString();
                Boolean bySourcesS = bySource.isChecked();
                String sourcesL = sources.getSelectedItem().toString();
                String countryL = country.getSelectedItem().toString();
                String categoryL = category.getSelectedItem().toString();
                listener.makeAdvanceSearch(topicS, bySourcesS, sourcesL, countryL, categoryL);
            }
        });

        topic = view.findViewById(R.id.headline_topic);
        bySource = view.findViewById(R.id.sources);
        sources = view.findViewById(R.id.source_spinner);
        country = view.findViewById(R.id.country_spinner);
        category = view.findViewById(R.id.category_spinner);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AdvanceDialogListener) context;
        } catch (ClassCastException e) {
           throw new ClassCastException(context.toString() + "must implement AdvanceSearchDialog");
        }
    }

    public interface AdvanceDialogListener{
        void makeAdvanceSearch(String topic, Boolean bySource, String source, String country, String category);
    }
}
