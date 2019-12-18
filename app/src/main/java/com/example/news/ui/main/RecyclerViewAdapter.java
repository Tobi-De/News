package com.example.news.ui.main;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.Article;
import com.example.news.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private ArrayList<Article> myValues;
    private OnItemClickListener mListener;

    RecyclerViewAdapter(ArrayList<Article> myValues){
        this.myValues= myValues;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_view, parent, false);
        return new MyViewHolder(listItem, mListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String description = myValues.get(position).getDescription();
        String imageUrl = myValues.get(position).getUrlToImage();
        String source = myValues.get(position).getSource();
        String date = myValues.get(position).getPublishedAt();
        holder.titleView.setText(myValues.get(position).getTitle());
        holder.descriptionView.setText(description.equals("null") ? "" : description);
        holder.sourceView.setText(source.equals("null") ? "" : source);
        holder.dateView.setText(formatedDate(date));

        if(imageUrl.equals("null")){
            holder.imageView.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp);
        }else {
            holder.imageView.setImageURI(Uri.parse(imageUrl));
            Picasso.get().load(myValues.get(position).getUrlToImage()).into(holder.imageView);
        }
    }

    private String formatedDate(String date) {
        if(date.equals("null")){
            return "";
        }else {
            String[] fullDate = date.split("T");
            String dateL = fullDate[0];
            String hour = fullDate[1].split(":")[0];
            String minutes = fullDate[1].split(":")[1];
            return  hour + ":" + minutes + " | " + dateL;
        }
    }


    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private TextView descriptionView;
        private ImageView imageView;
        private TextView sourceView;
        private TextView dateView;
        MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            descriptionView = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.image);
            sourceView = itemView.findViewById(R.id.source);
            dateView =itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
