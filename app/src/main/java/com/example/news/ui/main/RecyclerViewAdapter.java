package com.example.news.ui.main;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.news.Article;
import com.example.news.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private ArrayList<Article> myValues;
    private OnItemClickListener mListener;

    public RecyclerViewAdapter (ArrayList<Article> myValues){
        this.myValues= myValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_view, parent, false);
        return new MyViewHolder(listItem, mListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String description = myValues.get(position).getDescription();
        String imageUrl = myValues.get(position).getUrlToImage();
        holder.titleView.setText(myValues.get(position).getTitle());
        holder.descriptionView.setText(description.equals("null") ? "": description);

        if(imageUrl.equals("null")){
            holder.imageView.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp);
        }else {
            holder.imageView.setImageURI(Uri.parse(imageUrl));
            Picasso.get().load(myValues.get(position).getUrlToImage()).into(holder.imageView);
        }
    }


    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private TextView descriptionView;
        private ImageView imageView;
        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            descriptionView = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.image);

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
